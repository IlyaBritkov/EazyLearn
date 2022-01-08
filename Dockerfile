FROM adoptopenjdk/openjdk11

# cd /opt/app
WORKDIR /opt/app

# Refers to Maven build -> finalName
ARG JAR_FILE=target/spring-boot-eazylearn-app.jar

#Copy spring-boot-eazylearn-app.jar to /opt/app/app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]