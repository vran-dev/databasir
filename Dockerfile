FROM gradle:7.3-jdk11 as build
WORKDIR /app
ADD . /app
RUN gradle api:build

FROM openjdk:11.0.13-jre
WORKDIR /app
COPY --from=build /app/api/build/libs/databasir.jar /app/databasir.jar
EXPOSE 8080

#-Ddatabasir.datasource.username=${databasir.datasource.username}
#-Ddatabasir.datasource.password=${databasir.datasource.password}
#-Ddatabasir.datasource.url=${databasir.datasource.url}
ENTRYPOINT ["sh", "-c","java ${JAVA_OPTS} -jar /app/databasir.jar"]
