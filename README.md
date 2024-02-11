# Image-Resizing-WebApp

## Overview

The Image-Resizing-WebApp is a distributed system project that enables users to resize images through a web interface. The application is built with Java using Remote Method Invocation (RMI) to distribute the image processing load across two servers. This project is designed to demonstrate the use of Java RMI, servlets and Docker for creating a scalable and distributed web application.

## Development Environment

- **Java Development**: Developed using Eclipse with JavaSE-17.
- **Docker Image**: Based on `critoma/linux-u20-dev-security-ism` from [Docker Hub](https://hub.docker.com/r/critoma/linux-u20-dev-security-ism).
- **Runtime Environment in Docker**:
  - Java JDK 21
  - Apache TomEE Plume 9.1.2 
    
## Structure

The project is structured into three main folders:

- **Image-Resizing-WebApp**: Contains the web application files, including the `index.html` and Java servlets for processing image resize requests.
- **FirstRmiServer**: Contains the first RMI server implementation, including the `RmiServerInterface`, `RmiServerImpl`, and the main program class for the server.
- **SecondRmiServer**: Similar to the FirstRmiServer, this folder contains the components for the second RMI server.

Additionally, a `cmdCompile.txt` file is included to guide the compilation and container setup process.

## Deployment

The application is containerized using Docker, simplifying deployment and scaling. It utilizes a custom Docker image based on `critoma/linux-u20-dev-security-ism` for a consistent development and runtime environment. The setup involves running multiple containers to host the web application and RMI servers, with detailed instructions provided in `cmdCompile.txt`.

## Running the Application

1. Export the RMI servers and web application as JAR/WAR files as per instructions in `cmdCompile.txt`.
2. Deploy the Docker containers for the RMI servers and the web application using the provided commands.
4. Access the web application through a browser at [http://localhost:8080/ImageResizingWebApp/index.html](http://localhost:8080/ImageResizingWebApp/index.html).

## Screenshot

![image-resizing-webapp](https://github.com/annam015/Image-Resizing-WebApp/assets/92727258/88909e98-b9ff-4571-bc92-96113e362442)
