def BATCH_SIZE = 100
def DUMMY_VALUE = ""  // actual value is in the key

def flowFiles = session.get(BATCH_SIZE)

for (flowFile in flowFiles) {
    def topic = flowFile.getAttribute('kafka.topic')
    def partition = flowFile.getAttribute('kafka.partition')
    def offset = flowFile.getAttribute('kafka.offset')

    session.putAttribute(flowFile, "kafka-provenance-$topic-$partition-$offset", DUMMY_VALUE)

    session.transfer(flowFile, REL_SUCCESS)
}
