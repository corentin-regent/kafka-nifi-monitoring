#!/bin/bash

set -e

hdfs namenode -format

echo "Starting HDFS NameNode and DataNode..."
hdfs namenode &
hdfs datanode &

# Keep container running
tail -f /dev/null
