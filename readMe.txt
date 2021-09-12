Run Instructions
--- ------------
Software Requirements
-------- ------------
1) JDK 11
2) Maven
3) IDE to open the project as Java application.
Steps to boot up the application
----- -- ---- -- --- -----------
1) Open the application in an IDE as a Java project. This will allow all needed dependencies to be downloaded
automatically from maven repository based on pom.xml defined.
2) Once all the dependencies are downloaded, the application is ready to be started. To start the application open the file InterviewApplication and run the application as Spring boot application by clicking on the
green arrow on the IDE.No additional parameters are needed to run the application. The application runs on the default profiles.
3) If the application starts to boot successfully then the following log will be visible on the console
    Start of InterviewApplication Booting
    The application takes some time to boot up completely as it loads data in memory
    and the following log will be shown on console when the cache is loading in memory
    In progress character cache creation....
4) Once the application has booted up completely the following log will be visible on the console
    End of InterviewApplication Booting
    *** ** ******************** *******
5) When the above log is visible then it means that all data and api keys needed for the application
    are loaded successfully and the application is ready to accept the user requests
6) Swagger documents are present in the following location
    http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
7)  The application runs on the following port http://localhost:8080
8) The h2 console is available on the following url http://localhost:8080/h2-console. The credentials for logging into
    h2 console is defined in application.properties.

