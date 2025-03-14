# Spring Boot with PostgreSQL Demo

This is a simple Spring Boot application that demonstrates integration with PostgreSQL using Docker Compose.

## Prerequisites

- Java 23
- Docker and Docker Compose
- Maven (or you can use the included Maven wrapper)

## Running the Application

There are two ways to run the application:

### 1. Using Spring Boot's built-in Docker Compose support

Simply run:

```bash
./mvnw spring-boot:run
```

Spring Boot will automatically start the required Docker containers defined in `docker-compose.yml`.

### 2. Using the provided shell script

Run:

```bash
./run.sh
```

This script will:
1. Start the Docker containers with `docker compose up -d`
2. Wait for PostgreSQL to be ready
3. Run the Spring Boot application

## Application Structure

- `src/main/java/info/jab/ms/MainApplication.java` - Spring Boot application entry point
- `src/main/java/info/jab/ms/controller` - REST controllers
- `src/main/java/info/jab/ms/service` - Service layer
- `src/main/java/info/jab/ms/repository` - Repository layer and entities
- `src/main/resources/schema.sql` - Database schema definition

## API Endpoints

- `GET /actors` - List all actors

## Docker Compose

The `docker-compose.yml` file defines a PostgreSQL container with:

- Database name: `demo`
- Username: `postgres`
- Password: `postgres`
- Port: `5432`

## Troubleshooting

If you encounter any issues with database connectivity, make sure:

1. Docker is running on your machine
2. No other service is using port 5432
3. Check Docker container logs with `docker logs postgres-demo`

```bash
sdk install springboot
spring init -d=web,devtools --build=maven --force ./

jbang setup@jabrena init --cursor java

./mvnw clean verify

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
```
