FROM apache/nifi:1.28.1

ENV SINGLE_USER_CREDENTIALS_USERNAME=user
ENV SINGLE_USER_CREDENTIALS_PASSWORD=password1234

COPY ./core-site.xml /opt/hadoop/etc/core-site.xml
COPY ./hdfs-site.xml /opt/hadoop/etc/hdfs-site.xml

COPY ./scripts /app/scripts

# NiFi Web UI
EXPOSE 8443
