
# Request to withdraw v1

`Here, requestToWithdrawV1() creates a POST request to collection/v1_0/requesttowithdraw`

> `This operation is used to request a withdrawal (cash-out) from a consumer (Payer). The payer will be asked to authorize the withdrawal. The transaction will be executed once the payer has authorized the withdrawal`

### Usage/Examples

Construct a withdraw model and set desired values

```java


        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("5.0");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        withdraw.setPayer(payer);
```



```java

       
        SDKManager.collection.requestToWithdrawV1(withdraw, "<callbackUrl>", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse withdrawResponse) {
             
            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
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


