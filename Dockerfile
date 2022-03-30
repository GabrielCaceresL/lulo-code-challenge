FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY "./build/libs/lulo-code-challenge-1.0.jar" "lulo-code"
ENTRYPOINT ["java","-jar", "lulo-code"]