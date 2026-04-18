# TV Tracker API

Spring Boot API for searching and retrieving media data from external providers, with a provider-agnostic internal model that the frontend can consume consistently.

## What It Does

- Search media across providers
- Fetch movie and TV details from TMDB
- Fetch season details for TV series
- Keep provider DTOs separate from the app's own response models

AniList is currently used for anime search, but the project is already structured so that provider can be replaced later without rewriting the main media flow.

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
+-- model
+-- provider
|   +-- anilist
|   +-- tmdb
```

The current code is already close to a feature-first structure. See [architecture.md](/architecture.md) for the recommended next iteration.

## Running Locally

### 1. Set environment variables

TMDB is required for movie and TV endpoints:

```powershell
$env:TMDB_TOKEN="your_tmdb_bearer_token"
```

If you later keep AniList locally, add its credentials the same way or move them into Spring configuration when needed.

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
GET /media/search?query=one piece&type=anime
```

`type` is optional. Supported values:

- `movie`
- `tv`
- `anime`

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

- Move shared domain models under `media`
- Extract duplicated enums into shared types
- Add controller-level exception handling
- Add mapper and service tests
- Keep provider-specific DTOs and names out of the public model

## OpenAPI

There is an early OpenAPI draft in [openapi.yaml](/openapi.yaml). It still needs alignment with the implemented routes before treating it as the source of truth.
