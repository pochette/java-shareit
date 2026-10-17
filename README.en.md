# ShareIt

> REST API for an item-sharing service: listing, search, booking and reviews after rental.

![Java](https://img.shields.io/badge/Java_21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.3-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL_16-4169E1?logo=postgresql&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641)
![Docker](https://img.shields.io/badge/Docker_Compose-2496ED?logo=docker&logoColor=white)

[Русский](README.md) · **English**

## About

A backend for sharing things. An owner lists an item; other users find it through search and book it for specific dates. The owner approves or rejects the booking, and after the rental ends the renter can leave a review.

**Key decisions:**

- **Booking business rules.** Item availability is checked. Owners cannot book their own items, only the owner can approve a booking, and only the parties of a booking can view it. Violations return clear error statuses.
- **State filtering.** Bookings of a renter or an owner are selected by state: `ALL`, `CURRENT`, `PAST`, `FUTURE`, `WAITING`, `REJECTED`.
- **Enriched responses.** For the owner, an item card includes the last and next bookings as well as reviews.
- **Code quality.** Checkstyle, SpotBugs and JaCoCo are wired into Maven; the service layer is covered with Mockito unit tests.

## Tech stack

| Area | Technologies |
| --- | --- |
| Language and framework | Java 21, Spring Boot 3.3 (Web, Data JPA, Validation, Actuator) |
| Data | PostgreSQL 16, Hibernate; H2 for tests |
| Testing | JUnit 5, Mockito |
| Quality | Checkstyle, SpotBugs, JaCoCo |
| Build and run | Maven, Docker, Docker Compose |

## Architecture

Packages are split by domain; each domain has controller → service → repository layers plus DTOs and mappers.

```text
src/main/java/ru/practicum/shareit
├── user/       # users
├── item/       # items, search, comments
├── booking/    # bookings, statuses and filters
├── request/    # item requests
└── exception/  # unified error handling
```

## Quick start

Requires JDK 21, Docker Desktop and a free port 8080.

```bash
mvn clean package -DskipTests
docker compose up --build
```

API: http://localhost:8080. PostgreSQL is exposed on port 6541.

## API

The current user is passed in the `X-Sharer-User-Id` header.

| Resource | Endpoints |
| --- | --- |
| Users | `POST /users`, `GET /users`, `GET /users/{id}`, `PATCH /users/{id}`, `DELETE /users/{id}` |
| Items | `POST /items`, `PATCH /items/{id}`, `GET /items/{id}`, `GET /items` (owner's items), `GET /items/search?text=` |
| Reviews | `POST /items/{id}/comment` — only after a completed rental |
| Bookings | `POST /bookings`, `PATCH /bookings/{id}?approved=`, `GET /bookings/{id}`, `GET /bookings?state=`, `GET /bookings/owner?state=` |

## Data storage

Entities: `User`, `Item`, `Booking`, `Comment`, `ItemRequest`. Booking statuses: `WAITING`, `APPROVED`, `REJECTED`.

## Testing

```bash
mvn test
```

Service-layer unit tests with JUnit 5 and Mockito: adding items and reviews, fetching by id, search and handling of missing entities. The API is additionally checked with a [Postman collection](postman/sprint.json).

## Author

**Andrew Burdak** — [GitHub](https://github.com/pochette) · [Telegram @burdak_aa](https://t.me/burdak_aa) · [burdak.andrew@gmail.com](mailto:burdak.andrew@gmail.com)
