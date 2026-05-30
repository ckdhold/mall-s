# 多阶段构建：无需本机安装 Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
ARG MODULE=mall-admin
# 跳过 fabric8 docker-maven-plugin（镜像由本 Dockerfile 构建）
RUN mvn clean package -DskipTests -Ddocker.skip=true -pl ${MODULE} -am -B -q

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
ARG MODULE=mall-admin
ARG JAR_FILE=mall-admin-1.0-SNAPSHOT.jar
COPY --from=builder /app/${MODULE}/target/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
