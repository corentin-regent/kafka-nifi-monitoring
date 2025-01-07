FROM apache/hadoop:3.4.1

COPY ./core-site.xml /opt/hadoop/etc/hadoop/core-site.xml
COPY ./hdfs-site.xml /opt/hadoop/etc/hadoop/hdfs-site.xml
COPY ./entrypoint.sh /app/entrypoint.sh

ENTRYPOINT [ "/app/entrypoint.sh" ]

# File system
EXPOSE 9000

# NameNode REST API
EXPOSE 9870
