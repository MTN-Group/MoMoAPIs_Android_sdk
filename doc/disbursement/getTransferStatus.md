
# Get Transfer Status

`Here, getRefundStatus() creates a POST request to disbursement/v1_0/transfer/{referenceId}`

> `This operation is used to get the status of a transfer. X-Reference-Id that was passed in the post is used as reference to the request.`


### Usage/Examples


```java

      SDKManager.disbursement.getTransferStatus(referenceId, new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {
          
            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }

        });
```


### Example Output

```json
 
{
	"financialTransactionId": "117184837",
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


