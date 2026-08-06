# ShareIt

> REST API для сервиса аренды вещей между пользователями.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Hibernate-success)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Maven](https://img.shields.io/badge/Maven-3-red)
![JUnit5](https://img.shields.io/badge/JUnit-5-green)
![Mockito](https://img.shields.io/badge/Mockito-5-lightgrey)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)

---

## 📖 О проекте

**ShareIt** — это backend-приложение, реализующее сервис аренды вещей между пользователями.

Пользователь может разместить вещь, разрешить другим пользователям бронировать её, подтверждать или отклонять бронирования, а после завершения аренды пользователи могут оставлять отзывы.

Проект разработан в рамках курса **«Java-разработчик» Яндекс Практикума** и полностью построен на стеке Spring Boot.

---

# Возможности

## Пользователи

* регистрация пользователей;
* изменение информации;
* получение пользователя по ID;
* просмотр списка пользователей.

---

## Вещи

* добавление вещей;
* изменение информации;
* получение информации о вещи;
* получение списка вещей владельца;
* поиск вещей по названию и описанию.

---

## Бронирования

Пользователь может:

* забронировать вещь;
* отменить бронирование;
* просматривать свои бронирования;
* просматривать бронирования своих вещей.

Владелец вещи может:

* подтвердить бронирование;
* отклонить бронирование.

Поддерживаются состояния:

* ALL
* CURRENT
* PAST
* FUTURE
* WAITING
* REJECTED

---

## Комментарии

После окончания аренды пользователь может оставить отзыв о вещи.

Комментарии отображаются вместе с информацией о вещи.

---

## Запросы вещей

Реализована возможность:

* создавать запросы на вещи;
* получать собственные запросы;
* просматривать запросы других пользователей;
* получать вещи, соответствующие запросу.

---

# Используемые технологии

## Backend

* Java 21
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate

## База данных

* PostgreSQL
* H2 (для тестирования)

## Инструменты

* Maven
* Git
* Docker
* IntelliJ IDEA

## Тестирование

* JUnit 5
* Mockito
* Spring Boot Test
* MockMvc

---

# Архитектура

Проект построен по классической многослойной архитектуре.

```text
Controller
      │
      ▼
 Service
      │
      ▼
Repository
      │
      ▼
 PostgreSQL
```

### Основные пакеты

```text
src/main/java
│
├── controller
├── dto
├── mapper
├── service
├── repository
├── model
├── exception
├── validation
├── configuration
└── util
```

---

# Структура базы данных

Основные сущности:

* User
* Item
* Booking
* Comment
* ItemRequest

Связи:

* User → Item (OneToMany)
* User → Booking (OneToMany)
* Item → Booking (OneToMany)
* Item → Comment (OneToMany)
* ItemRequest → Item (OneToMany)

---

# REST API

## Пользователи

```http
POST /users
GET /users
GET /users/{id}
PATCH /users/{id}
DELETE /users/{id}
```

---

## Вещи

```http
POST /items
PATCH /items/{id}
GET /items/{id}
GET /items
GET /items/search
```

---

## Бронирования

```http
POST /bookings
PATCH /bookings/{id}
GET /bookings
GET /bookings/{id}
GET /bookings/owner
```

---

## Комментарии

```http
POST /items/{id}/comment
```

---

## Запросы вещей

```http
POST /requests
GET /requests
GET /requests/all
GET /requests/{id}
```

---

# Пример JSON

## Создание вещи

```json
{
  "name": "Mountain Bike",
  "description": "Trek Marlin 7",
  "available": true
}
```

---

## Создание бронирования

```json
{
  "itemId": 12,
  "start": "2026-08-10T09:00:00",
  "end": "2026-08-12T18:00:00"
}
```

---

# Обработка ошибок

Реализована централизованная обработка исключений.

Возвращаются корректные HTTP-коды:

* 400 Bad Request
* 404 Not Found
* 409 Conflict

---

# Тестирование

Проект содержит:

* модульные тесты сервисного слоя;
* тесты контроллеров;
* интеграционные тесты;
* тестирование репозиториев;
* использование MockMvc;
* использование Mockito.

---

# Запуск проекта

## Клонирование

```bash
git clone https://github.com/pochette/module-4-later-spring-only.git
```

---

## Сборка

```bash
mvn clean install
```

---

## Запуск

```bash
mvn spring-boot:run
```

или

```bash
docker compose up --build
```

После запуска приложение доступно по адресу

```
http://localhost:8080
```

---

# Что было изучено

Во время разработки проекта были освоены:

* Java Core
* Collections Framework
* Stream API
* Optional
* Generics
* REST API
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate ORM
* PostgreSQL
* H2 Database
* Maven
* Docker
* Git
* JUnit 5
* Mockito
* MockMvc
* Валидация данных
* Обработка исключений
* DTO и маппинг
* Проектирование многослойной архитектуры
* Работа с SQL и ORM

---

# Планы по развитию

* JWT-аутентификация
* Spring Security
* OpenAPI (Swagger)
* GitHub Actions (CI)
* Testcontainers
* Кэширование
* Мониторинг (Actuator)
* Логирование (SLF4J + Logback)

---

# Автор

**Андрей**

Студент 3 курса направления **«Прикладная информатика»** Академия ИМСИТ, г. Краснодар 

🎓  

GitHub: https://github.com/pochette
