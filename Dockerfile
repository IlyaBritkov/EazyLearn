FROM maven:3.8.4-jdk-11 AS build
RUN mkdir -p /workspace
WORKDIR /workspace

COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

FROM adoptopenjdk/openjdk11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]