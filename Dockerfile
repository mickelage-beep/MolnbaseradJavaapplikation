FROM eclipse-temurin:17-jdk-slim
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
ENTRYPOINT ["java","-jar","target/*.jar"]
