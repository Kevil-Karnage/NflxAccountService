# Account Service

## методы

### Получение баланса аккаунта
```
GET /account/{id}/balance  
```

### Пополнение баланса аккаунта
```
PUT /account/{id}/balance  
```
request body: 
```
{
    'amount': 123.12
}
```
