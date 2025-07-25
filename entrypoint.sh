#!/bin/bash
mkdir -p ./uploads
./mvnw clean package -DskipTests
java -jar target/hello-0.0.1-SNAPSHOT.jar