# Spring Microservices in Action - Second Edition: Chapter 5

Overview
This chapter introduces the fundamental concept of centralized configuration using the Spring Cloud Config Server. It demonstrates how to externalize and manage the settings for your microservices, enabling dynamic updates and simplified operations.

By the time you are done, you will have built and deployed a complete microservice ecosystem consisting of:

Config Service: A Spring Cloud Config server deployed as a Docker container, managing configuration from a file system/classpath or a GitHub-based repository.

Licensing Service: A primary microservice responsible for managing licensing data, which fetches its configuration from the Config Service.

PostgreSQL Database: The backing data store used by the Licensing Service.

Prerequisites
To build and run the services in this chapter, ensure you have the following installed:

Apache Maven: (<http://maven.apache.org>)

Java Development Kit (JDK): Version 11 or newer (All code examples were compiled with Java 11).

Git Client: (<http://git-scm.com>)

Docker Desktop: (<https://www.docker.com/products/docker-desktop>) - Required for running the services and the database via docker-compose.

Running Chapter 5 Services (Build & Deploy)
The services are packaged as Docker images and orchestrated using docker-compose. Follow these three steps from your command line to get the entire stack running.

Step 1: Clone the Repository and Navigate

# Clone the source code repository

$ git clone [https://github.com/ihuaylupo/manning-smia](https://github.com/ihuaylupo/manning-smia)

# Navigate into the directory containing the Chapter 5 source code

$ cd manning-smia/chapter5

Step 2: Build the Docker Images
This command will compile all Spring Boot projects in the directory and use the configured Spotify Dockerfile Maven Plugin (defined in pom.xml) to build the Docker images for both the config-service and licensing-service.

# Build all Spring Boot applications and their Docker images

$ mvn clean package dockerfile:build

Step 3: Start the Microservice Stack
Use docker-compose to launch the PostgreSQL database, the Config Server, and the Licensing Service using the images built in the previous step.

# Start all services defined in the docker-compose file

$ docker-compose -f docker/docker-compose.yml up

If everything starts correctly, you will see output from all services (PostgreSQL, Config Service, and Licensing Service) indicating that they are running and communicating.

Command Breakdown
The Build Command (mvn clean package dockerfile:build)
This single command executes the build for all nested Spring projects in the chapter's directory. If successful, you should see a message confirming the build finished successfully for all services.

The Run Command (docker-compose -f docker/docker-compose.yml up)
This command reads the service definitions in the docker/docker-compose.yml file and launches the entire application environment. This includes starting the PostgreSQL container, followed by the Config Server, and finally the Licensing Service, which relies on the Config Server for its startup configuration.

Database
The necessary PostgreSQL database script is included in the docker directory for database initialization.

Contact
I'd like you to send me an email on <g.dakthak@gmail.com> about anything you'd want to say about this software.

Contributing
Feel free to file an issue if it doesn't work for your code sample. Thanks.
