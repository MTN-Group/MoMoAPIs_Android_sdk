
# Get Deposit Status

`Here, getDepositStatus() creates a POST request to disbursement/v1_0/deposit/{referenceId}`

> `This operation is used to get the status of a deposit. X-Reference-Id that was passed in the post is used as reference to the request.


### Usage/Examples


```java

   SDKManager.disbursement.getDepositStatus("<reference id>", new DepositStatusInterface() {
            @Override
            public void onDepositStatusInterfaceSuccess(DepositStatus deposit) {
            
            }

            @Override
            public void onDepositStatusInterFaceFailure(MtnError mtnError) {

}
        });
```


### Example Output

```json
 
 {
	"externalId": "6353636",
	"amount": "5",
	"currency": "EUR",
	"payee": {
		"partyIdType": "MSISDN",
		"partyId": "0248888736"
	},
	"payerMessage": "Pay for product a",
	"payeeNote": "payer note",
	"status": "SUCCESSFUL"
}
 

```


