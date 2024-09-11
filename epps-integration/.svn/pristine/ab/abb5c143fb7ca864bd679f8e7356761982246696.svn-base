FROM amazoncorretto:8u292-alpine-jre as builder

VOLUME /tmp
WORKDIR epps-integration
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} epps-integration.jar
RUN java -Djarmode=layertools -jar epps-integration.jar extract

FROM amazoncorretto:8u292-alpine-jre
WORKDIR epps-integration
COPY --from=builder epps-integration/dependencies/ ./
COPY --from=builder epps-integration/spring-boot-loader/ ./
COPY --from=builder epps-integration/snapshot-dependencies/ ./
COPY --from=builder epps-integration/application/ ./
EXPOSE 10082



ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

##STEPS TO RUN WITH DOCKER
#Build your Docker image using the following command. 
#docker build -t repo/ayup/epps-integration --force-rm=true .

#After the build completes, tag your image so you can push the image to this repository:
#docker tag repo/ayup/epps-integration:latest 856505212696.dkr.ecr.ap-southeast-1.amazonaws.com/repo/ayup/epps-integration:latest

#For deploying application image in your docker container and using PORT 8086:
#docker run -it --publish 10082:10082 --name epps-integration repo/ayup/epps-integration