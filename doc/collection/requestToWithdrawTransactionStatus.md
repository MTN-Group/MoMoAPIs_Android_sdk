
# Request to withdraw status

`Here, requestToWithdrawTransactionStatus() creates a POST request to collection/v1_0/requesttowithdraw/{referenceId}`

> `This operation is used to get the status of a request to pay. X-Reference-Id that was passed in the post is used as reference to the request.`

### Usage/Examples


```java

       
       SDKManager.collection.requestToWithdrawTransactionStatus("<reference id>", new RequestToWithdrawStatusInterface() {
            @Override
            public void onRequestToWithdrawStatusSuccess(WithdrawStatus withdrawStatus) {
             
            }
            @Override
            public void onRequestToWithdrawStatusFailure(MtnError mtnError) {

}
        });
     
```


### Example Output

```json
 {
 	"financialTransactionId": "1680501894",
 	"externalId": "6353636",
 	"amount": "5",
 	"currency": "EUR",
 	"payer": {
 		"partyIdType": "MSISDN",
 		"partyId": "0248888736"
 	},
 	"payerMessage": "Pay for product a",
 	"payeeNote": "payer note",
 	"status": "SUCCESSFUL"
 }
```


