# Image de base Java 17
FROM eclipse-temurin:17-jdk-jammy

# Création d'un dossier de travail
WORKDIR /app

# Copier le jar compilé
COPY target/backend-java-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de l'API
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java","-jar","app.jar"]
