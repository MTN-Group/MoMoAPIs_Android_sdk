
# Get Refund Status

`Here, getRefundStatus() creates a POST request to disbursement/v1_0/refund/{referenceId}`

> `This operation is used to get the status of a refund. X-Reference-Id that was passed in the post is used as reference to the request`


### Usage/Examples


```java

   SDKManager.disbursement.getRefundStatus(referenceId, new RefundStatusInterface() {
            @Override
            public void onRefundStatusInterfaceSuccess(RefundStatus refundStatus) {
           
            }

            @Override
            public void onRefundStatusInterFaceFailure(MtnError mtnError) {

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


