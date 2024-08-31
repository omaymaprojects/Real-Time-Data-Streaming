FROM maven:3.6.3-jdk-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM flink:1.16.0
COPY --from=build /app/target/flink-app.jar /flink-app.jar
CMD ["flink", "run", "/flink-app.jar"]
