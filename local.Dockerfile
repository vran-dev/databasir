FROM openjdk:11.0.13-jre
WORKDIR /app
ADD api/build/libs/databasir.jar /app/databasir.jar
EXPOSE 8080

ENTRYPOINT ["sh", "-c","java -XX:+PrintCommandLineFlags -Ddatabasir.db.url=${DATABASIR_DB_URL} -Ddatabasir.db.username=${DATABASIR_DB_USERNAME} -Ddatabasir.db.password=${DATABASIR_DB_PASSWORD} -jar /app/databasir.jar ${PARAMS}"]