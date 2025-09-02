# SecureJwTeaTime

SecureTeaTime is a demo API built with Spring Boot and Spring Security to demonstrate the use of JWT authentication.
This project serves as a personal Spring Security template, designed to be a starting point for building secured
applications.

## This project contains the following features:

- Login with access & refresh tokens
    * An access token will be returned to the client.
    * A refresh token will be stored in HTTP only cookie.
    * The refresh token can be used to refresh the access token when it is expired.
    * On logout current access token will be blacklisted
    * During authentication the filter checks if token is black-listed, if so access is denied.
- Account activation via e-mail
- Password reset: request reset -> receive email -> reset password -> confirmation email

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

## What runs where

* The API will be running on [8080](http://localhost:8080/)
* MailDev runs on [1080](http://localhost:1080/)
* PG admin runs on [5050](http://localhost:5050/)

> When registering a new server on pg admin you should use `SecureJwTeaTime_postgres` as the hostname

## Demo

<img
src="https://github.com/lvanrooijen/SecureJwTeaTime/raw/main/resources/demo/demo.gif"
alt="play button"/>


