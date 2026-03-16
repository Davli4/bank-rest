# Тесты контроллеров

Unit тесты для REST API с использованием MockMvc.

## Классы
### AuthControllerTest

Тесты для контроллера аутентификации.

**Методы:**

1. `loginShouldReturn200WhenValidCredentials()` - проверка успешного входа в систему с валидными учетными данными.

**Что тестируется:**

1. Отправка POST запроса на `/api/auth/login` с JSON телом

2. Проверка статуса ответа 200 OK

### UserControllerTest

Тесты для контроллера пользователя.

**Методы:**

1. `getCurrentUserShouldReturnProfileWhenAuthenticated()` - проверка получения профиля аутентифицированным пользователем.

2. `getCurrentUserShouldReturn403WhenNotAuthenticated()` - проверка, что неаутентифицированный пользователь получает статус 403 Forbidden.

**Что тестируется:**

1. `GET` запрос на `/api/users/profile`

2. Мокирование UserService для возврата тестовых данных

3. Проверка статуса ответа и JSON полей (username, email)

4. Проверка доступа без аутентификации

### AdminControllerTest

Тесты для контроллера администратора.

**Методы:**

1. `getUsersShouldReturnPageWhenAdmin()` - проверка получения списка всех пользователей.

2. `makeAdminShouldReturnUserWhenAdmin()` - проверка назначения пользователя администратором.

3. `deactivateShouldReturnMessageWhenAdmin()` - проверка деактивации пользователя.

4. `getAllCardsShouldReturnPageWhenAdmin()` - проверка получения всех карт с фильтрацией.

5. `blockCardShouldReturnCardWhenAdmin()` - проверка блокировки карты.

6. `deleteCardShouldReturnMessageWhenAdmin()` - проверка удаления карты.

7. `getStatsShouldReturnTotalUsersWhenAdmin()` - проверка получения статистики системы.

**Что тестируется:**

1. Все эндпоинты доступны только с ролью `ADMIN` (через `@WithMockUser(roles = "ADMIN")`)

2. Мокирование сервисов для возврата тестовых данных

3. Проверка статуса ответа `200 OK`

4. Проверка JSON полей в ответах