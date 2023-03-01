
# Validate account holder status


`Here, validateAccountHolderStatus() creates a POST request to disbursement/1_0/accountholder/{msisdn}/{value}/active`

> `Validate account holder can be used to do a validation if a customer is active and able to receive funds. The use case will only validate that the customer is available and active. It does not validate that a specific amount can be receivedn `

### Usage/Examples

Construct a account identifier object and set desired values

```java

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId("0248888736");


        SDKManager.disbursement.validateAccountHolderStatus(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
              
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
            }
        });
    }


```
### Example Output

```json
{
	"result": true
}

```


