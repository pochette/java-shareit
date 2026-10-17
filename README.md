# ShareIt

> REST API сервиса аренды вещей: размещение, поиск, бронирование и отзывы после аренды.

![Java](https://img.shields.io/badge/Java_21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.3-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL_16-4169E1?logo=postgresql&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641)
![Docker](https://img.shields.io/badge/Docker_Compose-2496ED?logo=docker&logoColor=white)

**Русский** · [English](README.en.md)

## О проекте

Бэкенд для шеринга вещей. Владелец размещает вещь, другие пользователи находят её через поиск и бронируют на нужные даты. Владелец подтверждает или отклоняет бронь, а после завершения аренды арендатор может оставить отзыв.

**Ключевые решения:**

- **Бизнес-правила бронирования.** Проверяется доступность вещи. Владелец не может забронировать свою вещь, подтвердить бронь может только он, а просматривать её — только участники сделки. Нарушения возвращаются с понятными статусами ошибок.
- **Фильтрация по состояниям.** Бронирования арендатора и владельца выбираются по состояниям `ALL`, `CURRENT`, `PAST`, `FUTURE`, `WAITING`, `REJECTED`.
- **Обогащённые ответы.** Карточка вещи для владельца содержит последнее и ближайшее бронирование, а также отзывы.
- **Качество кода.** Checkstyle, SpotBugs и JaCoCo подключены в Maven, сервисный слой покрыт юнит-тестами на Mockito.

## Стек

| Область | Технологии |
| --- | --- |
| Язык и фреймворк | Java 21, Spring Boot 3.3 (Web, Data JPA, Validation, Actuator) |
| Данные | PostgreSQL 16, Hibernate; H2 для тестов |
| Тесты | JUnit 5, Mockito |
| Качество | Checkstyle, SpotBugs, JaCoCo |
| Сборка и запуск | Maven, Docker, Docker Compose |

## Архитектура

Пакеты разделены по предметным областям, внутри каждой — слои controller → service → repository, а также DTO и мапперы.

```text
src/main/java/ru/practicum/shareit
├── user/       # пользователи
├── item/       # вещи, поиск, комментарии
├── booking/    # бронирования, статусы и фильтры
├── request/    # запросы на вещи
└── exception/  # единая обработка ошибок
```

## Быстрый старт

Нужны JDK 21, Docker Desktop и свободный порт 8080.

```bash
mvn clean package -DskipTests
docker compose up --build
```

API: http://localhost:8080. PostgreSQL доступен на порту 6541.

## API

Текущий пользователь передаётся в заголовке `X-Sharer-User-Id`.

| Ресурс | Эндпоинты |
| --- | --- |
| Пользователи | `POST /users`, `GET /users`, `GET /users/{id}`, `PATCH /users/{id}`, `DELETE /users/{id}` |
| Вещи | `POST /items`, `PATCH /items/{id}`, `GET /items/{id}`, `GET /items` (вещи владельца), `GET /items/search?text=` |
| Отзывы | `POST /items/{id}/comment` — только после завершённой аренды |
| Бронирования | `POST /bookings`, `PATCH /bookings/{id}?approved=`, `GET /bookings/{id}`, `GET /bookings?state=`, `GET /bookings/owner?state=` |

## Хранение данных

Сущности: `User`, `Item`, `Booking`, `Comment`, `ItemRequest`. Статусы брони: `WAITING`, `APPROVED`, `REJECTED`.

## Тестирование

```bash
mvn test
```

Юнит-тесты сервисного слоя на JUnit 5 и Mockito: добавление вещи и отзыва, получение по id, поиск и обработка отсутствующих сущностей. API дополнительно проверяется [Postman-коллекцией](postman/sprint.json).

## Автор

**Андрей Бурдак** — [GitHub](https://github.com/pochette) · [Telegram @burdak_aa](https://t.me/burdak_aa) · [burdak.andrew@gmail.com](mailto:burdak.andrew@gmail.com)
