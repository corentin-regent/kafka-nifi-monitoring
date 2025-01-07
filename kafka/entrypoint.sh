#!/bin/bash

set -e

BROKER=kafka:29092
INPUT_FILES=/app/data/*
TOPIC_NAME=input-topic

echo "Starting Kafka..."
/etc/confluent/docker/run &

echo "Waiting for Kafka to be ready..."
until kafka-broker-api-versions --bootstrap-server "$BROKER" >/dev/null 2>&1
do
    sleep 5
done

# The topic is created automatically upon production
# The consumer groups are created automatically upon consumption

echo "Publishing the input messages to Kafka..."
for file in $INPUT_FILES
do
    key=$(echo "$file" | rev | cut -d "/" -f1 | rev)  # file name
    value=$(cat "$file" | tr -d '\n')  # content without \n, otherwise it creates 1 message per \n
    message="${key}:${value}"
    kafka-console-producer \
        --bootstrap-server "$BROKER" \
        --topic "$TOPIC_NAME" \
        --property "parse.key=true" \
        --property "key.separator=:" \
        <<< "$message"
done

echo "Kafka is set up and ready to go!"

# Keep the container running
tail -f /dev/null
