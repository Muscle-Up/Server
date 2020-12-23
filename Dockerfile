FROM java:8
COPY ./build/libs/*.jar app.jar
RUN mkdir -p /home/ubuntu/expert /home/ubuntu/profile /home/ubuntu/body
ENTRYPOINT ["java","-jar", "-Xmx300M","/app.jar"]
EXPOSE 8000