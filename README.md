# TV Tracker API

Spring Boot API for searching and retrieving movie and TV data from TMDB, with an internal model that the frontend can consume consistently.

## What It Does

- Search movies and TV shows with TMDB
- Fetch movie and TV details from TMDB
- Fetch season details for TV series
- Keep TMDB DTOs separate from the app's own response models

## Stack

- Java 21
- Spring Boot 4
- Spring MVC
- Spring WebClient
- Spring Security
- Lombok
- Maven

## Project Structure

```text
src/main/java/com/lbranco/tv_tracker_api
|
+-- config
+-- media
|   +-- controller
|   +-- service
|   +-- model
+-- provider
|   +-- tmdb
+-- shared
|   +-- enums
|   +-- exception
```

The current code follows this feature-first structure now. See [architecture.md](/architecture.md) for the design rules behind it.

## Running Locally

### 1. Set environment variables

TMDB is required for search and media endpoints:

```powershell
$env:TMDB_TOKEN="your_tmdb_bearer_token"
```

### 2. Start the app

```powershell
.\mvnw.cmd spring-boot:run
```

Or run tests:

```powershell
.\mvnw.cmd test
```

## API Endpoints

### Search

```http
GET /media/search?query=naruto
GET /media/search?query=dark&type=tv
GET /media/search?query=inception&type=movie
```

`type` is optional. Supported values:

- `movie`
- `tv`

### Media Details

```http
GET /media/{type}/{id}
```

Examples:

```http
GET /media/movie/550
GET /media/tv/1399
```

### Season Details

```http
GET /media/tv/{seriesId}/season/{seasonNum}
```

Example:

```http
GET /media/tv/1399/season/1
```

## Design Notes

- Controllers are intentionally thin.
- Application services decide which provider to call.
- Provider clients own HTTP details.
- Provider mappers convert external DTOs into internal models.
- The internal models are the API contract the frontend sees.

## Current Improvement Priorities

- Add mapper and service tests
- Add API-level request/response DTOs if the public contract starts diverging from the internal model
- Add more explicit provider exception types
- Add controller-level tests for validation and error handling
- Keep provider-specific DTOs and names out of the public model

## OpenAPI

There is an early OpenAPI draft in [openapi.yaml](/openapi.yaml). It still needs alignment with the implemented routes before treating it as the source of truth.
