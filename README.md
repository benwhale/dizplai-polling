# dizplai-polling

Dizplai Technical Test

## Installation

- Requires Docker (testcontainers)

### Backend

The backend utilises Java 21 and Spring Boot built with Maven

```bash
cd backend
mvn clean install
```

### Frontend

```bash
cd frontend
npm install
```

## Assumptions and Decisions

### React application

Selected create-react-app as I've used it before. Vite would have been another option and potentially good for this small-scale application, but I haven't used it yet, so wanted to save any learning curve.

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
