# TV Tracker API – Architecture Overview

This document explains the high-level architecture of the backend and how requests flow through the system.

---

# 🔄 Request Flow (End-to-End)

```
Client (Frontend)
        ↓
Controller
        ↓
Service
        ↓
Provider Client (AniListClient)
        ↓
External API (AniList)
        ↓
Response DTO
        ↓
Mapper / Adapter
        ↓
Domain Model
        ↓
Service
        ↓
Controller
        ↓
Client (Frontend)
```

---

# 🧱 Layer Responsibilities

## 1. Controller Layer

- Handles HTTP requests
- Validates input
- Calls service layer

Example responsibility:
- `/media/search`

---

## 2. Service Layer

- Contains business logic
- Orchestrates calls to providers
- Decides which provider to use

Example:
- Search media across AniList, TMDB, etc.

---

## 3. Provider Client Layer

- Handles HTTP communication with external APIs
- Builds requests
- Parses responses into DTOs

Example:
- AniListClient

---

## 4. DTO Layer

- Represents external API structures
- Matches JSON exactly

Example:
- AniListMediaRequest
- AniListMediaResponse

---

## 5. Mapper / Adapter Layer

- Converts external DTOs → internal models
- Keeps your domain independent of providers

Example:
- AniListMediaResponse → Media

---

## 6. Domain Model Layer

- Internal representation of your data
- Used across your application

Example:
- Media
- MediaDetails
- Season

---

# 🧭 Example Flow (Search Media)

1. Frontend calls:
   ```
   GET /media/search?query=naruto
   ```

2. Controller receives request

3. Controller calls Service:
   ```
   mediaService.search("naruto")
   ```

4. Service decides to use AniListProvider

5. Service calls AniListClient:
   ```
   aniListClient.search("naruto")
   ```

6. Client sends GraphQL request to AniList API

7. AniList returns JSON

8. Client deserializes into AniListMediaResponse (DTO)

9. Mapper converts DTO → Media model

10. Service returns MediaList

11. Controller returns response to frontend

---

# 📦 Suggested Package Structure

```
com.lbranco.tv_tracker_api

├── controller
│   └── MediaController.java

├── service
│   └── MediaService.java

├── provider
│   ├── anilist
│   │   ├── AniListClient.java
│   │   ├── AniListService.java
│   │   ├── dto
│   │   │   ├── AniListMediaRequest.java
│   │   │   └── AniListMediaResponse.java
│   │   └── mapper
│   │       └── AniListMapper.java
│   │
│   └── tmdb
│       ├── TmdbClient.java
│       ├── dto
│       └── mapper
│
├── model
│   ├── Media.java
│   ├── MediaDetails.java
│   └── Season.java

└── config
```

---

# 🧠 Why This Architecture Works

- Decouples external APIs from your core logic
- Allows multiple providers (AniList, TMDB, etc.)
- Makes testing easier
- Keeps your domain stable even if APIs change

---

# ✅ Summary

- Controller = handles incoming HTTP requests
- Client = calls external APIs
- Service = orchestrates logic
- Mapper = transforms data between layers
- DTO = external data format
- Model = internal domain representation

---

This separation is the foundation of a scalable multi-provider system.

