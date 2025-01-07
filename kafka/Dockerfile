FROM confluentinc/confluent-local:7.8.0

ENV KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092"
ENV KAFKA_CONTROLLER_QUORUM_VOTERS="1@kafka:29093"
ENV KAFKA_LISTENERS="PLAINTEXT://kafka:29092,CONTROLLER://kafka:29093,PLAINTEXT_HOST://0.0.0.0:9092"

COPY ./entrypoint.sh /app/entrypoint.sh
COPY ./data /app/data

ENTRYPOINT [ "/app/entrypoint.sh" ]

# Kafka broker
EXPOSE 29092
