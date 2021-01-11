FROM openjdk:8-jdk-alpine
RUN addgroup -S test --gid 1000 && adduser --uid 1000 -S Arun -h /home/Arun -G test
USER Arun:test
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","app.jar"]
