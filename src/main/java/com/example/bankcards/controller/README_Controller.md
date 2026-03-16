# Контроллеры

REST-контроллеры для управления пользователями, картами и переводами.

## Классы

### AuthController
Контроллер аутентификации. Предоставляет эндпоинт для входа в систему и получения JWT токена.

**Основные компоненты:**
1. `POST /api/auth/login` - аутентификация пользователя
2. Использование `AuthenticationManager` для проверки учетных данных
3. Генерация JWT токена через `JwtTokenProvider`
4. Возврат токена и данных пользователя в `JwtResponse`

**Как работает:**
1. Клиент отправляет POST запрос с логином и паролем
2. Контроллер аутентифицирует пользователя через Spring Security
3. При успешной аутентификации генерируется JWT токен
4. Токен и данные пользователя возвращаются в ответе
5. Клиент использует полученный токен для последующих запросов

**Пример запроса:**
```json
POST /api/auth/login
{
  "username": "admin",
  "password": "admin123"
}
```

**Пример ответа:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc3MzUwMzk4OSwiZXhwIjoxNzczNTkwMzg5fQ.a_dMm0FSwVt5fcbaew4tkMvL5xvvTEwx7CaUvt-Bv2k",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@bank.com",
  "roles": ["ROLE_ADMIN", "ROLE_USER"]
}
```

### UserController
Контроллер для работы обычных пользователей с их картами и профилем.

**Основные компоненты:**
```
GET /api/users/profile - получение профиля текущего пользователя

GET /api/users/cards - получение всех карт пользователя с пагинацией

GET /api/users/cards/{id} - получение карты по ID

GET /api/users/cards/{id}/balance - получение баланса карты

GET /api/users/cards/status/{status} - получение карт по статусу

POST /api/users/cards/{id}/block - блокировка своей карты

POST /api/users/transfer - перевод между своими картами
```

**Как работает пагинация:**

Параметры: page (номер страницы), size (размер страницы), sort (сортировка)

По умолчанию: size=10, sort=id,asc

**Пример:**
```
/api/users/cards?page=0&size=5&sort=balance,desc
```

**Пример запроса перевода:**
```json
POST /api/users/transfer
{
  "fromCardId": 1,
  "toCardId": 2,
  "amount": 5000.00
}
```
**Пример ответа:**
```json
{
  "message": "Transfer successful"
}
```

### AdminController
Контроллер для административных функций. Доступен только пользователям с ролью ADMIN.

**Основные компоненты:**

**Управление пользователями**
```
GET /api/admin/users - получение всех пользователей

PUT /api/admin/users/{id}/role - назначение пользователя администратором

DELETE /api/admin/users/{id}/deactivate - деактивация пользователя
```
**Управление картами**

```GET /api/admin/cards - получение всех карт с фильтрацией

POST /api/admin/cards - создание карты для пользователя

PUT /api/admin/cards/{id}/block - блокировка карты

PUT /api/admin/cards/{id}/activate - активация карты

DELETE /api/admin/cards/{id} - удаление карты
```
**Статистика**

```json
GET /api/admin/stats - получение статистики системы
```
**Параметры фильтрации для GET /api/admin/cards:**

1. userId - ID пользователя (опционально)

2. status - статус карты (ACTIVE, BLOCKED, EXPIRED)

3. owner - имя владельца (частичное совпадение)

**Пример запроса с фильтрацией:**
```json
GET /api/admin/cards?userId=1&status=ACTIVE&owner=Ivan
```

**Пример создания карты:**

```json
POST /api/admin/cards?userId=1
{
"cardNumber": "1234567890123456",
"owner": "Ivan Petrov",
"expiryDate": "2025-12-31",
"initialBalance": 10000.00
}
```
**Пример ответа статистики:**

```json
GET /api/admin/stats
{
"totalUsers": 10
}
```

