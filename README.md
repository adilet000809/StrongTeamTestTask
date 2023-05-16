
# Strongte.am test task

An application for storing, processing and displaying news on Spring boot.


## Authentication

The only API endpoint with no authentication requirement is:

```http
  /api/auth/**
```
All other endpoints require authentication. In order to access those endpoints client must include JWT in 'Authorization header' with prefix 'Bearer '.

```http
  GET /echo/get/json HTTP/1.1
    Host: hostname
    Accept: application/json
    Authorization: Bearer <JWT>
```



## Auth API

### Login

```http
  POST /api/auth/login
```

#### Request. Provide username and password in JSON format.
```json
{
    "username": "Your username",
    "password": "Your password"
}
```
#### Response. Returns user info and JWT for access.
```json
{
    "user": {
        "id": ID,
        "username": "username"
    },
    "token": "JWT"
}
```
### Register

```http
  POST /api/auth/register
```
#### Request. Provide username and password in JSON format.
```json
{
    "username": "Your username",
    "password": "Your password"
}
```
#### Response. Returns user success message in JSON format.
```json
{
    "message": "User has been registered successfully."
}
```
-------------------------------------------------------------------
## News Source API

### Get all news source items

```http
  GET /api/news-sources
```
#### Returns all news source items in JSON format.
```json
[
    {
        "id": 1,
        "name": "News source name"
    },
    {
        "id": 2,
        "name": "News source name"
    }
]
```
### Get news source item by id

```http
  GET /api/news-sources/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news source |

#### Returns news source item in JSON format.

```json
{
    "id": 1,
    "name": "News source name"
}
```
### Create news source item

```http
  POST /api/news-sources
```
#### Request. Provide news source in JSON format.
```json
{
    "name": "News source name"
}
```
### Update news source item

```http
  PUT /api/news-sources
```
#### Provide news source id and news source in JSON format.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news source |

```json
{
    "name": "News source name"
}
```
### Delete news source item by id

```http
  DELETE /api/news-sources/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news source |

-------------------------------------------------------------------
## News Topic API

### Get all news topic items

```http
  GET /api/topics
```
#### Returns all news topic items in JSON format.
```json
[
    {
        "id": 1,
        "name": "News topic name"
    },
    {
        "id": 2,
        "name": "News topic name"
    }
]
```
### Get news topic item by id

```http
  GET /api/topics/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news topic |

#### Returns news topic item in JSON format.

```json
{
    "id": 1,
    "name": "News topic name"
}
```
### Create news topic item

```http
  POST /api/topics
```
#### Request. Provide news topic in JSON format.
```json
{
    "name": "News topic name"
}
```
### Update news topic item

```http
  PUT /api/topics
```
#### Provide news topic id and news topic in JSON format.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news topic |

```json
{
    "name": "News source name"
}
```
### Delete news topic item by id

```http
  DELETE /api/topic/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news topic |

-------------------------------------------------------------------

## News API

### Get all news items

```http
  GET /api/news
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `integer` |   **Not required** Default value (0) |
| `size` | `integer` |   **Not required** Default value (0) |

#### Returns all news items with pagination in JSON format.
```json
[
    {
        "id": 1,
        "title": "News title",
        "content": "Content",
        "dateOfPublish": "Date",
        "newsSource": {
            "id": 1,
            "name": "News source"
        },
        "topics": [
            {
                "id": 1,
                "name": "News topic"
            }
        ]
    }
]
```
### Get all news item by id

```http
  GET /api/news/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` |   **Required.** Id of news  |

#### Returns news item by id in JSON format.
```json
{
    "id": 1,
    "title": "News title",
    "content": "Content",
    "dateOfPublish": "Date",
    "newsSource": {
        "id": 1,
        "name": "News source"
    },
    "topics": [
        {
            "id": 1,
            "name": "News topic"
        }
    ]
}
```
### Create news item

```http
  POST /api/news
```
#### Request. Provide news in JSON format.
```json
{
    "title": "News title",
    "content": "News content",
    "newsSourceId": newsSourceId,
    "topicId":[topic1Id, topic2Id]
}
```

### Update news item

```http
  PUT /api/news/{id}
```
#### Request. Provide news in JSON format.
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` |   **Required.** Id of news  |

```json
{
    "title": "News title",
    "content": "News content",
    "newsSourceId": newsSourceId,
    "topicId":[topic1Id, topic2Id]
}
```
### Delete news item by id

```http
  DELETE /api/news/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Id of news news |

### Get all news items by news source id

```http
  GET /api/news/sources/{sourceId}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `sourceId` | `integer` |   **Required** News sources id |
| `page` | `integer` |   **Not required** Default value (0) |
| `size` | `integer` |   **Not required** Default value (0) |

#### Returns all news items by news source id with pagination in JSON format.
```json
[
    {
        "id": 1,
        "title": "News title",
        "content": "Content",
        "dateOfPublish": "Date",
        "newsSource": {
            "id": 1,
            "name": "News source"
        },
        "topics": [
            {
                "id": 1,
                "name": "News topic"
            }
        ]
    }
]
```
### Get all news items by news topic id

```http
  GET /api/news/topics/{topicId}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `topicId` | `integer` |   **Required** News topic id |
| `page` | `integer` |   **Not required** Default value (0) |
| `size` | `integer` |   **Not required** Default value (0) |

#### Returns all news items by news topic id with pagination in JSON format.
```json
[
    {
        "id": 1,
        "title": "News title",
        "content": "Content",
        "dateOfPublish": "Date",
        "newsSource": {
            "id": 1,
            "name": "News source"
        },
        "topics": [
            {
                "id": 1,
                "name": "News topic"
            }
        ]
    }
]
```
## Statistics API

### Get statistics file txt format

```http
  GET /api/statistics
```
#### Returns .txt file with statistics data.