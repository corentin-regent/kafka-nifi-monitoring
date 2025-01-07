## Prerequisites

Use Linux (or WSL), and install [Docker](https://docs.docker.com/engine/install/) with the [Docker Compose plugin](https://docs.docker.com/compose/install/linux/).

## Getting started

### Startup & Teardown

Run `docker compose up --build -d` to build and start local Kafka, NiFi and HDFS and instances. Note that one needs to wait a bit before the services are actually ready and operational.

Run `docker compose down` when you are done, in order to tear these services down.

### Kafka

A user interface for Kafka is started and available at: http://localhost:8080/

### NiFi

In order to access the NiFi UI, open the following URL in your browser: https://localhost:8443/nifi/

You may be warned that "your connection is not private", but you can proceed anyway. Then, authenticate using the following credentials:
- User: `user`
- Password: `password1234`

One can then import the [nifi/template.xml](/nifi/template.xml) template in NiFi in order to spawn all process groups.

### HDFS

The Hue UI for HDFS is available at localhost:8888/

You will be prompted an username and password; you can use whatever you want.

```sh
docker compose exec -it hdfs hdfs dfs -ls -R hdfs://hdfs:9000/
```
