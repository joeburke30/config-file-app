#!/bin/bash
#while true; do echo "foreground"; sleep 12; done
java -Djdk.tls.client.protocols=TLSv1.2 -jar /app.jar
