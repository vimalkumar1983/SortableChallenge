FROM openjdk:8
ADD target/SortableChallenge-0.0.1-SNAPSHOT.jar SortableChallenge.jar
RUN mkdir /files
COPY src/main/resources/AuctionResult.json /files/AuctionResult.json
COPY src/main/resources/Config.json /files/Config.json
COPY src/main/resources/Input.json /files/Input.json

ENTRYPOINT ["java","-jar","SortableChallenge.jar"]  
