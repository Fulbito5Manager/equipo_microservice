FROM openjdk:17-jdk
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8
VOLUME /tmp
WORKDIR /app
COPY target/equipo-0.0.1-SNAPSHOT.jar equipo_service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","equipo_service.jar"]


# Establecer el directorio de trabajo dentro del contenedor
# WORKDIR /app




