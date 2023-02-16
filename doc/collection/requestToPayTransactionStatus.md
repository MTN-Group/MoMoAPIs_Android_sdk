
# Get Request to pay transaction status

`Here, requestToPayTransactionStatus() creates a GET request to collection/v1_0/requesttopay/{referenceId}`

> `This operation is used to used for validating the status of the transaction `

### Usage/Examples

Passing the reference id obtained of transaction into requestToPayTransactionStatus() to get the status of the transaction


```java

   SDKManager.collection.requestToPayTransactionStatus("<Reference id of transaction>", new RequestPayStatusInterface() {
            @Override
            public void onRequestStatusSuccess(RequestPayStatus requestPayStatus) {
              
            }

            @Override
            public void onRequestStatusFailure(MtnError mtnError) {

            }
        });


```
### Example Output

```json
{
  "status": "true",
  "X-Reference-Id":"0a8b994d-969a-4ef0-92e3-0f7e14eb5b96"

}
```


