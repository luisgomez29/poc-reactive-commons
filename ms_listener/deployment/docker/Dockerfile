FROM eclipse-temurin:21-jdk-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY ms_listener.jar ms_listener.jar
RUN chown -R appuser:appgroup /app
ENV JAVA_OPTS="-XX:MaxRAMPercentage=70 -XX:+UseContainerSupport -XX:+ExitOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"
USER appuser
ENTRYPOINT [ "/bin/sh", "-c", "/opt/java/openjdk/bin/java $JAVA_OPTS -jar /app/ms_listener.jar" ]
