FROM openjdk:17-alpine

ENV APP_NAME hello-docker-1.0.0
	
COPY target/${APP_NAME}.jar ${APP_NAME}.jar
	
CMD java -jar ${APP_NAME}.jar

EXPOSE 8080