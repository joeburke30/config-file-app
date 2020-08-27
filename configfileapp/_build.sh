#!/bin/bash
mvn package
docker build -t joeburke30/localconfigtest:1.0 .
docker save  joeburke30/localconfigtest:1.0 | gzip > target/joeburke30-localconfigtest-1.0.tar.gz
echo "image saved to target/localconfigtest-1.0.tar.gz"

