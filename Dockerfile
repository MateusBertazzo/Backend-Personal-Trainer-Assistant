FROM maven:3-openjdk-17 as build-image

WORKDIR /to-build-app

COPY . .

RUN mvn dependency:go-offline
RUN mvn package -DskipTests

#SEGUNDO ESTÁGIO
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build-image /to-build-app/target/*.jar ./agrix.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar"]