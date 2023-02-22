
# Account Balance

`Here, getAccountBalance() creates a GET request to collection/v1_0/account/balance`

> `Get balance request is used to check the balance on the default account connected to the API User. The following is the sequence flow for get balance use case`

### Usage/Examples


```java

         SDKManager.collection.getAccountBalance(new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {
            
            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
            }
        });
     
```


### Example Output

```json
      {
      	"availableBalance": "0",
      	"currency": "EUR"
      }

```


