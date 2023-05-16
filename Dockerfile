FROM openjdk:17
RUN mkdir "stats"
ADD target/StrongTeamTestTask-0.0.1-SNAPSHOT.jar StrongTeamTestTaskApp.jar
ENTRYPOINT ["java", "-jar", "StrongTeamTestTaskApp.jar"]