# Ресурсы

application.yml, настройки логов, конфигурации Liquibase и прочее.

**application.yml**

Основной конфигурационный файл Spring Boot.

**Общие настройки**

```yaml
spring:
  application:
    name: bank-rest
```

**База данных**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5434/bank_db
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
```

**Параметры подключения:**

1. Хост: localhost

2. Порт: 5434

3. База данных: bank_db

4. Пользователь: postgres

5. Пароль: postgres

6. Максимальный размер пула: 10 соединений

7. Минимальное количество idle соединений: 5

8. Таймаут подключения: 30 секунд

9. JPA (Hibernate)

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true
    open-in-view: false
```

**Настройки:**

1. ddl-auto: validate - проверка соответствия Entity и таблиц БД (без автоматического изменения схемы)

2. dialect - диалект PostgreSQL

3. format_sql: true - форматирование SQL запросов в логах

4. show-sql: true - вывод SQL запросов в консоль

5. open-in-view: false - отключение Open Session in View

**Liquibase**


```yaml
spring:
  liquibase:
    change-log: classpath:db/migration/db.changelog-master.yml
    enabled: true
```
**Настройки:**

1. change-log - путь к главному файлу миграций

2. enabled: true - включение миграций при запуске

**Сервер**

```yaml
server:
  port: 8080
```
Порт, на котором запускается приложение.

**JWT**

```yaml
jwt:
  secret: ${JWT_SECRET:mySecretKeyForJWTGeneration2024WithLongLength123456789}
  expiration: 86400000
```
**Параметры:**

1. secret - секретный ключ для подписи токенов (можно переопределить через переменную окружения JWT_SECRET)

2. expiration - время жизни токена в миллисекундах (86400000 = 24 часа)

**Swagger / OpenAPI**

```yaml
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operations-sorter: method
    tags-sorter: alpha
    try-it-out-enabled: true
```
**Настройки:**

1. api-docs.path - путь к OpenAPI спецификации в формате JSON

2. swagger-ui.path - путь к интерфейсу Swagger UI

3. operations-sorter: method - сортировка операций по HTTP методу

4. tags-sorter: alpha - сортировка тегов по алфавиту

5. try-it-out-enabled: true - включение кнопки "Try it out" в Swagger UI

**Логирование**

```yaml
logging:
level:
com.example.bankrest: DEBUG
#org.springframework.security: DEBUG
#org.hibernate.SQL: DEBUG
#org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```
**Уровни логирования:**

1. com.example.bankrest: DEBUG - отладочные логи приложения

2. Закомментированные строки можно раскомментировать при необходимости:

3. org.springframework.security: DEBUG - логи безопасности

4. org.hibernate.SQL: DEBUG - вывод SQL запросов

5. org.hibernate.type.descriptor.sql.BasicBinder: TRACE - вывод параметров запросов