FROM gradle:7-jdk11 AS build

WORKDIR /home/gradle
COPY --chown=gradle:gradle . ./

RUN gradle installDist --no-daemon

FROM openjdk:11
EXPOSE 8000

RUN useradd -MrUd / -s /usr/sbin/nologin app

COPY --from=build /home/gradle/build/install/* /app
WORKDIR /app
COPY openapi.yaml ./

USER app
ENTRYPOINT ["/bin/sh", "/app/bin/amodeus-api"]
