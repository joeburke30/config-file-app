FROM adoptopenjdk/openjdk11:jre-11.0.8_10-centos
COPY  target/*.jar /app.jar
COPY  run.sh /run.sh
EXPOSE 8889
#ENTRYPOINT ["java","-Djdk.tls.client.protocols=TLSv1.2 -jar","/app.jar"]
ENTRYPOINT ["/run.sh"]
