# dizplai-polling

Dizplai Technical Test

## Installation

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

### CI/CD

Minimal CI using GitHub actions to build and test

## Workflow / Worklog

* High level planning, tech stack
* Initialised Spring Boot backend app (Spring Boot initializer) and React frontend app (create-react-app)
* Set-up basic CI pipeline in GitHub Repo
* Installed Postgres