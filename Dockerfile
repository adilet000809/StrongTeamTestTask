FROM openjdk:17
ADD target/StrongTeamTestTask-0.0.1-SNAPSHOT.jar StrongTeamTestTaskApp.jar
ENTRYPOINT ["java", "-jar", "StrongTeamTestTaskApp.jar"]