# Running the project

## Prerequisites

Use Linux (or WSL), and install [Docker](https://docs.docker.com/engine/install/) with the [Docker Compose plugin](https://docs.docker.com/compose/install/linux/).

## Startup & Teardown

Run `docker compose up --build -d` to build and start local Kafka, NiFi and HDFS instances, as well as user interfaces. Note that one needs to wait a bit before the services are actually ready and operational.

If you don't have enough RAM to run all of these simultaneously, you can run only the services you need, by listing them in the command, e.g. `docker compose up --build -d kafka nifi hdfs`.

Run `docker compose down` when you are done, in order to tear these services down.

## Kafka

A user interface for Kafka is started and available at http://localhost:8080/.

By default, the `input-topic` topic is populated with a few messages. You can create new ones manually by opening a shell in the Kafka container using `docker compose exec -it kafka sh`, and then run producer commands like the following:

```sh
kafka-console-producer \
    --bootstrap-server "kafka:29092" \
    --topic "your-topic" \
    --property "parse.key=true" \
    --property "key.separator=:" \
    <<< "your-key:your-value"
```

## NiFi

In order to access the NiFi UI, open the following URL in your browser: https://localhost:8443/nifi/. You may be warned that "your connection is not private", but you can proceed anyway. Then, authenticate using the following credentials:
- User: `user`
- Password: `password1234`

One can then import the [nifi/template.xml](/nifi/template.xml) template in NiFi in order to spawn all process groups.

## HDFS

The Hue UI for HDFS is available at http://localhost:8888/. You will be prompted an username and password; you can use whatever you want.

Alternatively, you can run DFS commands through the HDFS container, after opening a shell using `docker compose exec -it hdfs sh`. [Here](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html) is the reference. For example, you can list all the files stored in HDFS using `hdfs dfs -ls -R hdfs://hdfs:9000/`

## Monitoring

A Scala script is available under [monitoring/FetchProvenanceFromNifi.scala](/monitoring/FetchProvenanceFromNifi.scala), for listing the Kafka provenance of the ingested messages, by communicating with the NiFi DistributedMapCacheServer. It can be run using Docker:

```sh
docker run --rm -it --volume "$PWD/monitoring":/app --network kafka-nifi-monitoring_default sbtscala/scala-sbt:eclipse-temurin-alpine-21.0.5_11_1.10.7_3.6.3 scala /app/FetchProvenanceFromNifi.scala
```

# References

- ExecuteScript Cookbook: Part [1](https://community.cloudera.com/t5/Community-Articles/ExecuteScript-Cookbook-part-1/ta-p/248922), [2](https://community.cloudera.com/t5/Community-Articles/ExecuteScript-Cookbook-part-2/ta-p/249018) and [3](https://community.cloudera.com/t5/Community-Articles/ExecuteScript-Cookbook-part-3/ta-p/249148)
- [Inspecting your Nifi DistributedMapCacheServer with Groovy](https://funnifi.blogspot.com/2016/04/inspecting-your-nifi.html)
