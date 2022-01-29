# profile-matcher-service

Player profile matching microservice based on `Redis`. 

## Build

Build the project by using the gradle `gradlew build` command.

## Local development and testing

In this scope, the following Spring Boot profiles are provided and preconfigured:
* `mock` -> this will mock the campaigns service and provide a static response
* `local-redis-docker` -> this will start a Redis instance in Docker on application startup, and it will pre-initialize a `PlayerProfile` with id `97983be2-98b7-11e7-90cf-082e5f28d836` 

