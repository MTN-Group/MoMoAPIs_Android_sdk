
# Deposit v1 

`Here, depositV1() creates a POST request to disbursement/v1_0/deposit`

> `Deposit operation is used to deposit an amount from the owner’s account to a payee account`

### Usage/Examples

```java

 Deposit deposit = new Deposit();

 deposit.setAmount("5.0");
 deposit.setCurrency("EUR");
 deposit.setExternalId("6353636");
 deposit.setPayerMessage("Pay for product a");
 deposit.setPayeeNote("payer note");

 Payee payee = new Payee();
 payee.setPartyId("0248888736");
 payee.setPartyIdType("MSISDN");
 deposit.setPayee(payee);



```



```java

         SDKManager.disbursement.depositV1(deposit, "<callbackUrl>", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
           
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
            
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


