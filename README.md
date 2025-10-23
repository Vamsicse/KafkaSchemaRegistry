# KafkaSchemaRegistry

docker-compose down

docker-compose up -d



// Create Topic:

docker exec broker kafka-topics --create --topic tax-operations-tin-certification-status --bootstrap-server broker:29092 --partitions 1 --replication-factor 1


// Start Producer (Avro)


// NoAuth

docker run --rm -it \
  --platform linux/amd64 \
  --network tax-operations confluentinc/cp-schema-registry:7.5.0 kafka-avro-console-producer \
  --broker-list broker:29092 \
  --topic tax-operations-tin-certification-status \
  --property schema.registry.url=http://schema-registry:8081 \
  --property value.schema='{
    "type":"record",
    "name":"TinCertificationStatusAVRO",
    "namespace":"com.thrivent.tax.lettermanager.model",
    "fields":[
      {"name":"taxClientId","type":"long"},
      {"name":"docTypeCode","type":"string"},
      {"name":"statusCode","type":"string"},
      {"name":"createdDate","type":"string"},
      {"name":"operatorIdCode","type":"string"}
    ]
  }'



docker run --rm -it \
  --platform linux/amd64 \
  --network tax-operations confluentinc/cp-schema-registry:7.5.0 kafka-avro-console-producer \
  --broker-list broker:29092 \
  --topic tax-operations-tin-certification-status \
  --property schema.registry.url=http://schema-registry:8081 \
  --property schema.registry.basic.auth.credentials.source=USER_INFO \
  --property schema.registry.basic.auth.user.info=admin:password123 \
  --property value.schema='{
    "type":"record",
    "name":"TinCertificationStatusAVRO",
    "namespace":"com.thrivent.tax.lettermanager.model",
    "fields":[
      {"name":"taxClientId","type":"long"},
      {"name":"docTypeCode","type":"string"},
      {"name":"statusCode","type":"string"},
      {"name":"createdDate","type":"string"},
      {"name":"operatorIdCode","type":"string"}
    ]
  }'

When prompted, paste JSON messages like:
Examples:

{"taxClientId": 1001, "docTypeCode": "W9", "statusCode": "PENDING", "createdDate": "2025-10-20T20:15:00.000Z", "operatorIdCode": "ADMIN001"}
{"taxClientId": 1002, "docTypeCode": "W9", "statusCode": "PENDING", "createdDate": "2025-10-20T20:15:00.000Z", "operatorIdCode": "ADMIN002"}
{"taxClientId": 1009, "docTypeCode": "W9", "statusCode": "PENDING", "createdDate": "2025-10-20T20:15:00.000Z", "operatorIdCode": "ADMIN009"}
{"taxClientId": 1009, "docTypeCode": "W9", "statusCode": "PENDING", "createdDate": "2025-10-20T20:15:00.000Z", "operatorIdCode": "VAI"}
{"taxClientId": 1009, "docTypeCode": "W9", "statusCode": "PENDING", "createdDate": "2025-10-20T20:15:00.000Z", "operatorIdCode": "KRISHNA"}


// Start Consumer (Avro)

docker run --rm -it \
  --platform linux/amd64 \
  --network tax-operations confluentinc/cp-schema-registry:7.5.0 kafka-avro-console-consumer \
  --bootstrap-server broker:29092 \
  --topic tax-operations-tin-certification-status \
  --property schema.registry.url=http://schema-registry:8081 \
  --from-beginning



docker run --rm -it \
  --platform linux/amd64 \
  --network tax-operations confluentinc/cp-schema-registry:7.5.0 kafka-avro-console-consumer \
  --bootstrap-server broker:29092 \
  --topic tax-operations-tin-certification-status \
  --property schema.registry.url=http://schema-registry:8081 \
  --property schema.registry.basic.auth.credentials.source=USER_INFO \
  --property schema.registry.basic.auth.user.info=admin:password123 \
  --from-beginning

**********************************************************************************************************


Tests:

curl http://localhost:8443/tinlettermanager/ping

Test Schema registry (unauthenticated)
curl http://localhost:8081/subjects

curl -u admin:password123 http://localhost:8081/subjects


See schema
curl --location 'http://localhost:8081/subjects/tax-operations-tin-certification-status-value/versions/latest'

