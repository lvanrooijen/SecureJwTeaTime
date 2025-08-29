# SecureJwTeaTime

SecureTeaTime is a simple API built with Spring Security to demonstrate the use of JWT tokens for securing endpoints.
This project serves as a personal Spring Security template, designed to be a starting point for building secured
applications.
The main goal is to provide a foundation for security configuration that I can build and improve on over time, adding
more features as I continue learning.

## Requirements

- Java 17+
- Maven
- Docker

## How to run

Clone the project:

             https://github.com/lvanrooijen/SecureJwTeaTime.git

Build containers:</br>
`cd SecureJwTeaTime`</br>
`docker compose up --build` </br></br>
Run backend: </br>
`mvn install` </br>
`mvn spring-boot:run`
or <a href="https://www.youtube.com/watch?v=MtaTKXJ89jk" target="_blank">
<img
src="https://github.com/user-attachments/assets/e2e07ab6-bfc0-4ee8-99cb-d595f129b9ba"
alt="play button"
width="21"
height="21"
/>
</a> </br></br>

## what runs where

* The API will be running on [8080](http://localhost:8080/)
* MailDev runs on [1080](http://localhost:1080/)
* PG admin runs on [5050](http://localhost:5050/)

> When registering a new server on pg admin you should use `SecureJwTeaTime_postgres` as the hostname

## Demo

<img
src="https://github.com/lvanrooijen/SecureJwTeaTime/raw/main/resources/demo/demo.gif"
alt="play button"/>
