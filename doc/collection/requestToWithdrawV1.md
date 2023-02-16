
# Request to withdraw v1

`Here, requestToWithdrawV1() creates a POST request to collection/v1_0/requesttowithdraw`

> `Get balance request is used to check the balance on the default account connected to the API User. The following is the sequence flow for get balance use case`

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
      	"availableBalance": "0",
      	"currency": "EUR"
      }

```


