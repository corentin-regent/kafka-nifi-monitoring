FROM apache/nifi:1.28.1

# Enable HTTP, disable HTTPS
ENV NIFI_WEB_HTTP_PORT=8080
ENV NIFI_WEB_HTTP_HOST=0.0.0.0
ENV NIFI_WEB_HTTPS_PORT=
ENV NIFI_WEB_HTTPS_HOST=

COPY ./core-site.xml /opt/hadoop/etc/core-site.xml
COPY ./hdfs-site.xml /opt/hadoop/etc/hdfs-site.xml

COPY ./jolt /app/jolt
COPY ./scripts /app/scripts

# NiFi Web UI
EXPOSE 8080
