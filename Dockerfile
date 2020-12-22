FROM java:8
COPY ./build/libs/*.jar app.jar
RUN mkdir /expert /profile /body
ENTRYPOINT ["java","-jar", "-Xmx300M","/app.jar"]
EXPOSE 8000