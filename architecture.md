# TV Tracker API Architecture

This project is in a good place for a side project: the provider integrations are already separated, the controller layer is thin, and the app has a stable internal model. The next step is not "more layers", but making the boundaries clearer so the code stays easy to change when you swap providers.

## Core Direction

Use a feature-first structure for your app code and keep provider-specific code isolated behind small integration modules.

That means:

- `media` owns the API use cases and internal response shapes
- `provider` owns external API details only
- `config` owns framework wiring
- domain objects should represent your app, not TMDB or AniList

This is a better fit than a global `controller/service/model` layout because the project is small and the main change driver is feature evolution, not team size.

## Recommended Package Structure

```text
com.lbranco.tv_tracker_api
|
+-- config
|   +-- SecurityConfig
|   +-- WebClientConfig
|
+-- media
|   +-- controller
|   |   +-- MediaController
|   +-- service
|   |   +-- MediaSearchService
|   |   +-- MediaDetailsService
|   |   +-- SeasonDetailsService
|   +-- model
|       +-- Media
|       +-- MediaDetails
|       +-- SeasonDetails
|       +-- Title
|
+-- provider
|   +-- tmdb
|   |   +-- TmdbClient
|   |   +-- TmdbService
|   |   +-- dto
|   |   +-- mapper
|   +-- anilist
|       +-- AniListClient
|       +-- AniListService
|       +-- dto
|       +-- mapper
|
+-- shared
|   +-- enums
|   +-- exception
|   +-- utils
|
+-- TvTrackerApiApplication
```

## Why This Structure Fits This Project

### 1. Feature packages scale better here

Your controller and application services are already grouped under `media`, which is the right instinct. If you later add `user`, `list`, or `tracking`, each feature can grow independently without everything being mixed into giant global folders.

### 2. Providers should behave like adapters

`provider.tmdb` and `provider.anilist` should be treated as integration modules:

- clients talk HTTP/GraphQL
- DTOs match provider payloads
- mappers convert provider DTOs into internal models
- provider services expose small, app-friendly operations

The rest of the app should not know or care how TMDB or any future provider structures its payloads.

### 3. Keep domain models provider-agnostic

Your internal models are the contract between the API layer and the integration layer. They should stay stable even if a provider changes.

Good examples:

- `Media`
- `MediaDetails`
- `SeasonDetails`
- `Title`

Avoid leaking provider terms like TMDB field names, GraphQL-specific names, or transport-specific shapes into these models.

## Request Flow

```text
Client
  -> MediaController
  -> MediaSearchService / MediaDetailsService / SeasonDetailsService
  -> Provider service
  -> Provider client
  -> External API
  -> Provider DTO
  -> Provider mapper
  -> Internal model
  -> Controller response
```

The important rule is:

`controller -> application service -> provider adapter -> external API`

Not:

`controller -> provider client directly`

## Naming Guidelines

Use names that describe responsibility, not implementation detail.

Good patterns:

- `MediaSearchService`
- `MediaDetailsService`
- `TmdbClient`
- `TmdbMapper`
- `getTvSeriesDetails`

Names to avoid as the project grows:

- generic names like `Utils`, `Helper`, `Manager`
- DTO names without provider context
- service names that hide intent like just `MediaService`

Prefer:

- `searchAnime`
- `searchMovie`
- `getMovieDetails`

Instead of vague names like:

- `search`
- `details`

when the narrower name improves readability inside provider modules.

## Model Guidance

A few model decisions will pay off quickly:

- move `model` under `media.model` so it belongs to the feature that uses it
- extract duplicated enums such as `Type`, `Source`, and `MediaFormat` into shared enums once the API stabilizes
- keep ID strategy consistent, for example provider-prefixed IDs such as `tmdb:123`
- keep score semantics consistent across providers, ideally one scale everywhere

## What Should Stay Out of the Domain Layer

These should remain provider-only:

- GraphQL request payload classes
- TMDB response DTOs
- provider pagination details
- provider-specific field names
- authorization/token handling

## Error Handling Direction

Right now the app mostly logs provider failures and either returns an empty list or throws a generic runtime exception. That is fine temporarily, but the next improvement should be a small shared exception layer:

- `ProviderRequestException`
- `ProviderUnavailableException`
- `InvalidMediaTypeException`

Then add a controller advice to map those into clean API responses.

That gives you:

- better frontend errors
- cleaner logs
- less repeated `try/catch` logic in services

## Configuration Direction

Configuration should come from Spring properties, not direct environment reads in business code.

Good:

- `tmdb.token=${TMDB_TOKEN:}`

Less ideal:

- calling `System.getenv()` inside clients

Keeping config in properties makes testing and deployment easier.

## Practical Next Refactors

These are the highest-value improvements from here:

1. Move `model` to `media.model`.
2. Extract shared enums used by both `Media` and `MediaDetails`.
3. Add `@ControllerAdvice` for consistent API errors.
4. Add tests around mapper behavior and service fallbacks.
5. Replace provider-specific branching in services with a provider strategy if the number of providers grows.

## Rule of Thumb

When adding code, ask:

- Is this app logic? Put it in `media`.
- Is this external API knowledge? Put it in `provider.<name>`.
- Is this framework wiring? Put it in `config`.
- Is this reusable across features? Put it in `shared`.

That separation will keep the project easy to evolve without forcing enterprise-level complexity too early.

