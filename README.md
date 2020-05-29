Sortable Auction Challenge Solution

Instructions for running with Docker:
1) Clone https://github.com/vimalkumar1983/SortableChallenge.git
2) From Command Prompt, go to root folder SortableChallenge.
3) Run docker build --no-cache -f Dockerfile -t sortablechallenge . (remember the dot at the end)
4) Once done, run docker run -p 8080:8080 sortablechallenge
5) To view the result open localhost:8080
 
Note: If changes to Config.json and/or Input.json are done, please repeat step 3 and 4. 


Instructions for running without Docker:
1) Clone https://github.com/vimalkumar1983/SortableChallenge.git
2) From Command Prompt, go to root folder SortableChallenge
3) Run command mvn clean install 
4) Run command java -jar SortableChallenge-0.0.1-SNAPSHOT.jar
5) To view the result open localhost:8080.

