FROM java:8
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Xmx300M","/app.jar"]
RUN chmod 755 /app.jar
EXPOSE 8000