
# Transfer  

> `Here, Transfer operation is used to transfer an amount from the own account to a payee account.Status of the transaction can validated by using the  
disbursement/v1_0/transfer`


Construct a transfer model and set desire values


```java

    Transfer transfer = new Transfer();
        transfer.setAmount("5.0");
        transfer.setCurrency("EUR");
        transfer.setExternalId("6353636");
        transfer.setPayerMessage("Pay for product a");
        transfer.setPayeeNote("payer note");

        Payee payee = new Payee();

        payee.setPartyId("0248888736");
        payee.setPartyIdType("MSISDN");

        transfer.setPayee(payee);


```


### Usage/Examples


```java

   
        SDKManager.disbursement.transfer(transfer, "<callbackurl>", new RequestInterface() {
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


