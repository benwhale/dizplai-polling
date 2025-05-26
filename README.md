# dizplai-polling

## Dizplai Technical Test

This repository contains my solution to the Dizplai technical test [BRIEF.md](BRIEF.md)

## Prerequisites

- Java 21
- Node.js 22.x
- Docker

## Installation

### Database

This project uses Postgres 17.5, which is managed using docker compose.

To stand it up, run the following in the root of the repository:

```
docker-compose up -d
```

This will stand up a Postgres instance on port 5432 and an Adminer instance on port 8080 in detached mode.

Default development credentials are provided in the docker-compose.yml and application.json files for simplicity. These should never be committed to a 'real' repository

### Backend

The backend utilises Java 21 and Spring Boot built with Maven

```bash
cd backend
mvn clean install
mvn spring-boot:run # Runs on port 8081
```

API Documentation is provided using a Swagger/OpenAPI spec, which is exposed at: [/swagger-ui](localhost:8081/swagger-ui)

### Frontend

```bash
cd frontend
npm install
npm start # Runs on port 3000
```

## Testing

### Backend Tests

```bash
cd backend
mvn test
```

I've provided an integration test for the vote flow (happy path) in `test/BackendApplicationTests.java` and unit tests for the vote service vote method in `test/services/VoteServiceTests.java`

### Frontend Tests

```bash
cd frontend
npm test
```

I've provided basic component tests for PollOptionButton in `src/components/Poll/PollOptionButton.spec.tsx` and for the service methods in `src/services/pollService.spec.ts`

## Assumptions and Decisions

### React application

Selected create-react-app as a build tool as I've used it before. Vite would have been another option and potentially good for this small-scale application, but I haven't used it yet, so wanted to save any learning curve.

### Java build tool

Selected Maven over Gradle as it is what I am currently most familiar with.

### Postgres

Running a local Postgres

### Scaling considerations

Given we need to store information about the votes cast (time but I could imagine wanting IP Address etc), Postgres felt like a decent choice.
If we were needing massive scale then it might make sense to have gone for a NoSQL Cassandra/Mongo/DynamoDB etc approach.

I think the immediate next step I would do with this application is add a Redis layer for quick vote tallying.
I'd probably look to increment counts in Redis and store the full records in Postgres. Could put a queue between the API event and the DB calls to protect it from demand surges.

### Race conditions

Utilising atomic operation to increment vote counts to try and handle concurent voting.

### CI/CD

Minimal CI using GitHub actions to build and test

## Workflow / Worklog

- High level planning, tech stack
- Initialised Spring Boot backend app (Spring Boot initializer) and React frontend app (create-react-app)
- Set-up basic CI pipeline in GitHub Repo
- Installed Postgres
- Created spring application structure
- Created end to end poll creation flow - possibly overdid the DTOs
- CI is now failing as the test is trying to connect to the database, so will resolve that next - possibly a good time to use H2 locally?
