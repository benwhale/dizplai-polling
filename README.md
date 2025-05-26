# dizplai-polling

## Dizplai Technical Test

This repository contains my solution to the Dizplai technical test [BRIEF.md](BRIEF.md)

## Prerequisites

- Java 21
- Node.js 22.x
- Docker

## Installation

### Quick Start

1. **Start Database**: `docker-compose up -d`
2. **Create Frontend Environment**: Create `frontend/.env` with `REACT_APP_API_BASE_URL=http://localhost:8081`
3. **Start Backend**: `cd backend && mvn spring-boot:run`
4. **Start Frontend**: `cd frontend && npm install && npm start`
5. **Create & Activate Poll**: Use API examples below or utilise the swagger UI at [http://localhost:8081/swagger-ui](http://localhost:8081/swagger-ui)
6. **Test**: Visit http://localhost:3000

### Database

This project uses Postgres 17.5, which is managed using docker compose.

To stand it up, run the following in the root of the repository:

```
docker-compose up -d
```

This will stand up a Postgres instance on port 5432 and an Adminer instance on port 8080 in detached mode.

Default development credentials are provided in the docker-compose.yml and application.properties files for simplicity. These should never be committed to a 'real' repository

### Environment Configuration

Create a `.env` file in the `frontend/` directory:

```
REACT_APP_API_BASE_URL=http://localhost:8081
```

This configures the frontend to connect to the backend API.

### Backend

The backend utilises Java 21 and Spring Boot built with Maven

```bash
cd backend
mvn spring-boot:run # Runs on port 8081
```

API Documentation is provided using a Swagger/OpenAPI spec, which is exposed at: [http://localhost:8081/swagger-ui](http://localhost:8081/swagger-ui)

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

I've provided basic component tests for PollOptionButton in `src/components/Poll/PollOptionButton.test.tsx` and for the service methods in `src/services/pollService.test.ts`

## Requirements Compliance

- **Polls can have between 2 and 7 options** - Validated in `PollCreationDTO` with `@Size(min = 2, max = 7)`
- **The website should be responsive** - CSS includes mobile breakpoints at 768px and 480px
- **Polls and votes should be stored in a database of some sort** - PostgreSQL with proper relational schema
- **New polls should be created through an API** - `POST /polls/` endpoint (see Swagger docs)
- **An API should be available to view individual votes for a given poll and the time the vote was made** - `GET /votes/poll/{pollId}` with timestamps
- **There should be an example of how you would test front and backend code. There is no need to test the entire code base** - Unit and Integration tests (backend) and component tests (frontend)
- **A README should be provided explaining how to run any code to allow us to test the solution** - This document

### API Usage Examples

### Creating a Poll

```bash
curl -X POST http://localhost:8081/polls/ \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Who will win the Premier League?",
    "options": ["Manchester City", "Arsenal", "Liverpool"]
  }'
```

### Viewing Individual Votes

```bash
# Get all votes for poll ID 1
curl http://localhost:8081/votes/poll/1
```

### Activating a Poll

```bash
curl -X POST http://localhost:8081/polls/1/activate
```

## Assumptions and Decisions

### React application

I'm most familiar with React, and selected create-react-app as a build tool as I've used it before. With hindsight, I think I should have chosen Vite as it is a more modern option and potentially better for this small-scale application, but I haven't used it yet, so wanted to save any learning curve.

### CSS

Given small scale, I avoided bringing in something like tailwind or component libraries. If I were to further develop this, I reckon I would switch to tailwind particularly to help with scaling the responsive CSS and utilise the class name based utilities.

I've also bundled all the CSS into App.css rather than breaking it into individual component files mainly for speed of development purposes. Maintaining this longer term I'd want to organise the components into their own folders and keep styles that only belong to them in their own CSS files.

### Java / Spring Boot

Selected Maven over Gradle as it is what I am currently most familiar with.

### Postgres

For the purposes of this exercise, I'm using a Postgres database managed using docker-compose.

- **Relational model**: We have clear relationships in the data model (Polls/Options/Votes)
- **ACID Compliance**: Vote integrity is essential, and provides consistent counting under concurrent load.
- **Audit Requirements**: Built-in timestamp support and efficient querying

#### Scaling considerations

Given we need to store information about the votes cast (time but I could imagine wanting IP Address, userID etc to try and audit voting), Postgres felt like a decent choice.
If we were needing massive scale then it might make sense to have gone for a NoSQL Cassandra/DynamoDB etc approach where we would have eventual consistency, but would be able to deal better with a write-heavy workload.

I think the immediate next step I would do with this application to scale is add a Redis layer for quick vote tallying.
I'd probably look to increment counts in Redis and store the full records in Postgres. Could put a queue between the API event and the DB calls to protect it from demand surges.

#### Race conditions

Utilising an atomic operation to increment vote counts to try and handle concurrent voting.

### CI/CD

Minimal CI using GitHub actions to build and test

### Security

No Spring Security has been implemented (out of scope). At the moment that means the poll administration APIs can be accessed freely

There are Postgres credentials included in the application properties and docker-compose. This is purely to make it easier to quickly spin up this exercise and verify it is working.

### Poll Creation / Admin UI

Requirements state new polls should be created through an API, and that an API should be available to view individual votes for a given poll and the time the vote was made -- this is available as an API only, the UI was assumed out of scope

## Troubleshooting

### Frontend shows "Loading..." indefinitely

- Ensure backend is running on port 8081
- Check that `.env` file exists in frontend directory
- Verify an active poll exists (create and activate one via API)

### Database connection errors

- Ensure Docker is running
- Run `docker-compose up -d` to start PostgreSQL
- Check port 5432 is not in use by another service

### Tests failing

- Ensure Docker is running (backend tests use Testcontainers)
- Run `mvn clean install` before running tests
