# Currency Accounts

Currency Account is a mini-project to exchange a money between sub-accounts in different currencies.

## API Reference

#### Register new account

Remember that `personalId` (PESEL) is unique and `startingBalanceInPLN` should be > 0

```http
  POST localhost:8080/api/accounts/register
```
Example request body:
```json
{
"firstName": "Patryk",
"lastName": "Wójcik",
"personalId": "88030246447",
"startingBalanceInPLN": 100
}
```
Response:
```json
{
    "account": {
        "id": 1,
        "firstName": "Patryk",
        "lastName": "Wójcik",
        "personalId": "88030246447"
    },
    "subAccounts": [
        {
            "id": 1,
            "accountId": 1,
            "currency": "USD",
            "balance": 0
        },
        {
            "id": 2,
            "accountId": 1,
            "currency": "PLN",
            "balance": 100
        }
    ]
}
```

#### Get account by personal Id

```http
  GET localhost:8080/api/accounts/{personalId}
```
Example request:
```http
  GET localhost:8080/api/accounts/88030246447
```
Response:
```json
{
    "account": {
        "id": 1,
        "firstName": "Patryk",
        "lastName": "Wójcik",
        "personalId": "88030246447"
    },
    "subAccounts": [
        {
            "id": 1,
            "accountId": 1,
            "currency": "USD",
            "balance": 0
        },
        {
            "id": 2,
            "accountId": 1,
            "currency": "PLN",
            "balance": 100
        }
    ]
}
```
When user is not found, enpoint will return `404` - `Not Found`

#### Exchange money between sub-accounts

```http
  POST localhost:8080/api/accounts/exchange
```
Example request body:
```json
{
  "personalId": "88030246447",
  "from": "PLN",
  "to": "USD",
  "amount": 10
}
```
Response:
```json
{
  "account": {
    "id": 1,
    "firstName": "Patryk",
    "lastName": "Wójcik",
    "personalId": "88030246447"
  },
  "subAccounts": [
    {
      "id": 1,
      "accountId": 1,
      "currency": "USD",
      "balance": 2.15
    },
    {
      "id": 2,
      "accountId": 1,
      "currency": "PLN",
      "balance": 100.0
    }
  ]
}
```