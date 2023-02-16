
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
 	"financialTransactionId": "529186712",
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


