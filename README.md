# Account Service - сервис для взаимодействия с аккаунтами

## Запуск
В application.yml Необходимо изменить следующие строки в соответствии с вашим данными
```
  datasource:
    url: jdbc:postgresql://<hostDB>:<port>/<databaseName>
    username: <user>
    password: <password>
  kafka:
    bootstrap-servers: <hostKafka>:<portKafka>
    topic: transaction
```
#

## Методы

### Получение баланса аккаунта
```
GET /account/{id}/balance  
```
Формат ответа:
```
{
    "accountId": 111222,
    "balance": 123.4
    "timestamp": <Data>
}
```
Коды возможных ошибок:
* 404 - Аккаунт не найден

#

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
Формат ответа:
```
{
    "transactionId": <UUID>,
    "newBalance": 123.12,
    "timestamp": <Date>
}
```
Коды возможных ошибок:
* 404 - Аккаунт не найден
* 502 - Ошибка сохранения транзакции в базу данных
* 502 - Ошибка отправки сообщения в Kafka