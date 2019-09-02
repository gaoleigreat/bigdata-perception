FROM java:8
VOLUME /temp
ADD  system-service-0.0.1-SNAPSHOT.jar system-service.jar
RUN bash -c 'touch /system-service.jar'
ENTRYPOINT ["java","-jar","/system-service.jar"]
