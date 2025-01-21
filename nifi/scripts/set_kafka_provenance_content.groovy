def BATCH_SIZE = 100
def KAFKA_PROVENANCE_PATTERN = ~/^kafka-provenance-(.+)-(\d+)-(\d+)$/

def stringBuilder = new StringBuilder()
def flowFiles = session.get(BATCH_SIZE)

for (flowFile in flowFiles) {
    stringBuilder.setLength(0)  // Reset
    stringBuilder.append("topic;partition;offset;filename\n")
    def filename = flowFile.getAttribute('filename')

    for (entry in flowFile.attributes) {
        def match = entry.key =~ KAFKA_PROVENANCE_PATTERN
        if (match) {
            def topic = match.group(1)
            def partition = match.group(2)
            def offset = match.group(3)
            stringBuilder.append("$topic;$partition;$offset;$filename\n")
        }
    }

    // Replace flowFile content with stringBuilder's
    flowFile = session.write(flowFile, { outputStream ->
        outputStream.write(stringBuilder.toString().bytes)
    } as OutputStreamCallback)

    session.transfer(flowFile, REL_SUCCESS)
}
