# Account Service

## методы

### Получение баланса аккаунта
```
GET /account/{id}/balance  
```

### Пополнение баланса аккаунта 
```
POST /account/{id}/balance  
```
В случае отсутствия аккаунта с таким id создастся новый аккаунт

request body: 
```
{
    'amount': 123.12
}
```
