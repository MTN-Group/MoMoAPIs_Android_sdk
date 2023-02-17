
# Request To Pay

`Here, requestToPay() creates a POST request to collection/v1_0/requesttopay`

> `This operation is used to request a payment from a consumer (Payer).  `

### Usage/Examples

Construct a request pay model and set desired parameters

```java

   
        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("5.0");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        requestPay.setPayer(payer);
```

```java


    SDKManager.collection.requestToPay(requestPay, "<CallbackUrl>", new RequestInterface() {
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


