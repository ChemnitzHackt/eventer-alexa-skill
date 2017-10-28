FROM gradle:jdk8-alpine AS builder
RUN mkdir eventer
WORKDIR /home/gradle/eventer
COPY build.gradle ./
COPY src src/
RUN gradle build


FROM openjdk:8-jre-alpine AS runner
WORKDIR /root/
COPY --from=builder /home/gradle/eventer/build/libs/eventer.jar ./eventer.jar
CMD java -jar eventer.jar
EXPOSE 8080

