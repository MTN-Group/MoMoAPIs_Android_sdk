
# Create Api Key

`Here, createApiKey() creates a POST request to apiuser/{referenceId}/apikey`

> `This endpoint allows momo user to create a api key in API manager portal`

### Usage/Examples

Passing the reference id of created user into createApiKey() to get the api key

```java

 SDKManager.authentication.createApiKey(userReferenceId, new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
             
            }
            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
             
            }
        });


```
### Example Output

```json
{
	"apiKey": "3c1f360a71474f2faea859a671a6caef"
}
```



    @Test
    public void getBalanceSpecificCurrencyNullToken() {

        AppConstants.COLLECTION_TOKEN = null;
        SDKManager.collection.getAccountBalanceInSpecificCurrency("USD",new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });
    }

    @Test
    public void getBalanceSpecificCurrencyNullCurrency() {

        SDKManager.collection.getAccountBalanceInSpecificCurrency(null,new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Currency cannot be empty or null", mtnError.getErrorBody().getMessage());
            }
        });
    }

    @Test
    public void getBalanceSpecificCurrencyEmptyCurrency() {

        SDKManager.collection.getAccountBalanceInSpecificCurrency("",new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Currency cannot be empty or null", mtnError.getErrorBody().getMessage());
            }
        });
    }
