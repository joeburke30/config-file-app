#!/bin/bash
mvn clean
mvn package -DskipTests
docker build -t joeburke30/localkubesecrettest:1.0 .
docker save joeburke30/localkubesecrettest:1.0 | gzip > target/joeburke30-localkubesecrettest-1.0.tar.gz

