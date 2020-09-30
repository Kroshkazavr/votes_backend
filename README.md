#Votes application.
Here is backend implementation for votes application.

#Development
Start docker container with MySQL DB:
`docker-compose up -d`
Connect to DB with following parameters:
Host: 127.0.0.1
Username: root
Password: root
Port: 3306

Make build locally:
- Start docker container with MySQL DB:`docker-compose up -d`
- Call maven build command: `mvn clean package`
Maven calls integration tests.

Project uses Lombok library. Make following steps if you are using IntelliJ IDEA:
1. Install Lombok Plugin.
 - Open Preferences -> Plugins and install a Lombok plugin. 
2. Enable annotation processing.
 - Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors
 - Select Enable annotation processing   
 