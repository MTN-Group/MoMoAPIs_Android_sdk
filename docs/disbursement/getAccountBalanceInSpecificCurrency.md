
# Account Balance in specific currency

`Here, getAccountBalanceInSpecificCurrency () creates a GET request to disbursement/v1_0/account/balance/{currency}`

> `Get the balance of the account. Currency parameter passed in GET`

### Usage/Examples


```java

          SDKManager.disbursement.getAccountBalanceInSpecificCurrency("<currency>",new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {
             
            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
            }
        });
     
```


### Example Output




