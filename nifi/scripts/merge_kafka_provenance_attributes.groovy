def BATCH_SIZE = 100
def KAFKA_PROVENANCE_PATTERN = ~/^kafka-provenance\/([^\/]+)\/(\d+)\/(\d+)$/

def topics = []
def partitions = []
def offsets = []

def flowFiles = session.get(BATCH_SIZE)

for (flowFile in flowFiles) {
    topics.clear()
    partitions.clear()
    offsets.clear()

    for (entry in flowFile.attributes) {
        def match = entry.key =~ KAFKA_PROVENANCE_PATTERN
        if (match) {
            topics.add(match.group(1))
            partitions.add(match.group(2))
            offsets.add(match.group(3))
        }
    }

    session.putAttribute(flowFile, "kafka-topics", topics.join(','))
    session.putAttribute(flowFile, "kafka-partitions", partitions.join(','))
    session.putAttribute(flowFile, "kafka-offsets", offsets.join(','))

    session.transfer(flowFile, REL_SUCCESS)
}
