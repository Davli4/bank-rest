# Конфигурация

Содержит классы конфигурации Spring Boot: JWT, безопасность, Swagger, CORS и т.п.

## Классы

### SwaggerConfig
Настройка OpenAPI (Swagger) для документирования REST API. Конфигурация добавляет поддержку JWT аутентификации в интерфейс Swagger UI.

**Основные компоненты:**
1. `customOpenAPI()` - создает кастомную конфигурацию OpenAPI
2. Security scheme "BearerAuth" для JWT токенов
3. Глобальный SecurityRequirement для всех эндпоинтов

**Как работает:**
1. При запуске приложения Swagger автоматически подхватывает эту конфигурацию
2. В Swagger UI появляется кнопка "Authorize"
3. Пользователь может вставить JWT токен в формате `Bearer <token>`
4. Все последующие запросы из Swagger будут содержать этот токен в заголовке

**Пример сгенерированной спецификации:**
```json
{
  "openapi": "3.0.1",
  "components": {
    "securitySchemes": {
      "BearerAuth": {
        "type": "http",
        "scheme": "bearer",
        "bearerFormat": "JWT"
      }
    }
  },
  "security": [{"BearerAuth": []}]
}
```