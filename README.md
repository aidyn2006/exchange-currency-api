# Документация по проекту **Technical Task Exchange Service**

---

## Описание проекта

Проект представляет собой REST API сервис для получения текущих обменных курсов валют с внешнего API. Для повышения производительности используется кэширование результатов с помощью Redis.

---

## Технологии

* Java 17+
* Spring Boot
* Apache HttpClient
* Redis (для кэширования)
* Docker + Docker Compose
* Maven

---

## Функционал

* Получение курса валют через GET и POST запросы.
* Кэширование курсов в Redis, чтобы не делать повторные вызовы к внешнему API.
* Обработка ошибок при вызове внешнего API.
* Логи для отладки и мониторинга.

---

## Запуск проекта

### 1. Клонирование репозитория

```bash
git clone https://github.com/your-repo/technical-task-exchange.git
```

---

### 2. Запуск через Docker Compose

В проекте есть `docker-compose.yml`, который поднимает:

* Redis (порт 6379)
* Сам Spring Boot приложение (порт 8080)

```bash
docker-compose up --build -d
```

Параметры подключения к Redis автоматически подставляются через переменные окружения.

---

### 3. Проверка запущенных контейнеров

```bash
docker ps
```

Должны увидеть два контейнера: `redis` и `spring-app`.

---

### 4. Логи приложения

Чтобы посмотреть логи приложения:

```bash
docker logs -f spring-app
```

---

## API

### Базовый URL

```
http://localhost:8080/api/v1/exchange
```

---

### GET /live

Получение текущего курса валют через параметры запроса.

**Запрос:**

```
GET /api/v1/exchange/live?source=USD&target=KZT
```

**Параметры:**

* `source` — исходная валюта (например, USD)
* `target` — целевая валюта (например, KZT)

**Ответ:**

```json
{
  "source": "USD",
  "target": "KZT",
  "rate": 450.55,
  "date": "2025-06-05 20:00:00 PM"
}
```

---

### POST /live

Получение курса валют через POST запрос с JSON телом.

**Запрос:**

```
POST /api/v1/exchange/live
Content-Type: application/json

{
  "from": "USD",
  "to": "KZT"
}
```

**Ответ:**

```json
{
  "source": "USD",
  "target": "KZT",
  "rate": 450.55,
  "date": "2025-06-05 20:00:00 PM"
}
```

---

### Ошибки

* Если API внешний сервис не отвечает или произошла ошибка, сервис возвращает HTTP 500 с сообщением об ошибке в логах.
* При отсутствии курса или некорректных данных — возвращается `null` в теле и статус 500.

---

## Кэширование с Redis

* Ключ в кеше — это строка вида `"USD_KZT"`.
* Значение — ответ с курсом валюты.
* При повторном запросе данные берутся из Redis без вызова внешнего API.
* Кэш обновляется автоматически при истечении срока жизни (TTL), который можно настроить в конфиге Spring.

---

## Swagger документация

Swagger UI доступен по адресу:

```
http://localhost:8080/swagger-ui.html
```

Там можно увидеть все эндпоинты, описание, пример запросов и ответов.

---

## Сборка и запуск без Docker

1. Сборка:

```bash
mvn clean package -DskipTests
```

2. Запуск:

```bash
java -jar target/your-app.jar
```

Убедитесь, что Redis запущен локально и доступны параметры подключения в `application.properties`.


---

## Дополнительные рекомендации

* Для изменения кэш-параметров — настройте Redis и Spring Cache в `application.properties` или `application.yml`.
* Добавьте в `.env` файл ключи и параметры, чтобы не хранить их в коде.
* Для работы с внешним API убедитесь, что в `ExchangeConfig` прописан правильный URL и ключ доступа.
