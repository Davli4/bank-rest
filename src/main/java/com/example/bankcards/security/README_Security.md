# Безопасность
Конфигурации и компоненты безопасности: JWT, фильтры, UserDetailsService.

## Классы

### JwtConfig

Конфигурация JWT параметров.

**Основные компоненты:**

`secret` - секретный ключ для подписи токенов (из application.yml)

`expiration` - время жизни токена в миллисекундах (из application.yml)

### JwtTokenProvider

Провайдер для работы с JWT токенами.

**Основные компоненты:**

1. `generateToken(Authentication authentication)` - генерация JWT токена

2. `getUsernameFromToken(String token)` - извлечение имени пользователя из токена

3. `validateToken(String token)` - проверка валидности токена

Алгоритм: HS256

### UserDetailsImpl

Реализация UserDetails для Spring Security.

**Основные компоненты:**

1. `id` - ID пользователя

2. `username` - имя пользователя

3. `email` - email пользователя

4. `password` - пароль (игнорируется при сериализации)

5. `authorities` - список ролей пользователя

**Статический метод:**

`build(User user)` - создает **UserDetailsImpl** из сущности **User**

### UserDetailServiceImpl

Сервис загрузки пользователя для Spring Security.

**Основные компоненты:**

1. `loadUserByUsername(String username)` - загрузка пользователя по имени

### JwtAuthenticationFilter

Фильтр для проверки JWT токена в каждом запросе.

**Основные компоненты:**

1. `doFilterInternal` - основная логика фильтрации

2. `parseJwt(HttpServletRequest request)` - извлечение токена из заголовка Authorization

**Как работает:**

Извлекает токен из заголовка `Authorization`: `Bearer <token>`

Валидирует токен через **JwtTokenProvider**

Загружает пользователя через UserDetailServiceImpl

Устанавливает аутентификацию в **SecurityContext**

### SecurityConfig

Главная конфигурация безопасности.

**Основные компоненты:**

1. `passwordEncoder()` - кодировщик паролей (BCrypt)

2. `authenticationManager()` - менеджер аутентификации

3. `filterChain(HttpSecurity http)` - настройка цепочки фильтров

**Настройки доступа:**

`/api/auth/**, /error` - разрешены без аутентификации

`/swagger-ui/**, /api-docs/** `- разрешены без аутентификации

**Все остальные запросы требуют аутентификации**

Статистика сессий: `STATELESS` (без сохранения состояния)

`CSRF`: отключен

`CORS`: включен