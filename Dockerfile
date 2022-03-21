FROM openjdk:15
EXPOSE 8080
ADD target/rho-1.jar rho.jar
ENTRYPOINT ["java","-jar","rho.jar"]