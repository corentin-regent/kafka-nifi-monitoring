import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket
import java.nio.charset.StandardCharsets

// Server response codes
val DIFFERENT_RESOURCE_VERSION = 21

// Operations, can be found here: https://github.com/apache/nifi/blob/main/nifi-extension-bundles/nifi-standard-services/nifi-distributed-cache-services-bundle/nifi-distributed-cache-protocol/src/main/java/org/apache/nifi/distributed/cache/operations/MapOperation.java
val CLOSE = "close"
val KEY_SET = "keySet"

val CACHE_SERVER_HOST = "nifi"
val CACHE_SERVER_PORT = 4559

object FetchProvenanceFromNifi extends App {
  println("Opening a socket to the distributed map cache server")
  val socket = new Socket(CACHE_SERVER_HOST, CACHE_SERVER_PORT)
  try {
    val output = new DataOutputStream(socket.getOutputStream)
    val input = new DataInputStream(socket.getInputStream)

    // Supported versions can be found here: https://github.com/apache/nifi/blob/main/nifi-extension-bundles/nifi-standard-services/nifi-distributed-cache-services-bundle/nifi-distributed-cache-protocol/src/main/java/org/apache/nifi/distributed/cache/protocol/ProtocolVersion.java
    println("Negotiating handshake/version")
    var protocolVersion = 3
    output.write("NiFi".getBytes(StandardCharsets.UTF_8))
    while ({
      output.writeInt(protocolVersion)
      output.flush()
      val status = input.read()
      status == DIFFERENT_RESOURCE_VERSION
    }) {
      protocolVersion = protocolVersion + 1
    }

    println("Retrieving all keys (<=> Kafka provenance values) in the cache:")
    output.writeUTF(KEY_SET)
    output.flush()
    println("topic;partition;offset")
    val keyCount = input.readInt()
    for (_ <- 0 until keyCount) {
      val length = input.readInt()
      val buffer = new Array[Byte](length)
      input.readFully(buffer)
      println(new String(buffer, StandardCharsets.UTF_8))
    }

    println("Closing the connection")
    output.writeUTF(CLOSE)
    output.flush()
  } finally {
    socket.close()
  }
}
