
# Create User

`Here, requestToPay() creates a POST request to collection/v1_0/requesttopay`

> `This operation is used to request a payment from a consumer (Payer).  `

### Usage/Examples

Construct a request pay model and set desired parameters

```java

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("<Amount>"); //eg: 5.0 
        requestPay.setCurrency("<CURRENCY>");//eg: USD,EUR..
        requestPay.setExternalId("<External Id>"); 
        requestPay.setPayerMessage("<Message>");
        requestPay.setPayeeNote("<Note>");

        Payer payer = new Payer();

        payer.setPartyId("<Party Id>");
        payer.setPartyIdType("<partyId type>");//eg: MSISDN

        requestPay.setPayer(payer);

```

```java


    SDKManager.collection.requestToPay(requestPay, "<CallbackURl>", new RequestInterface() {
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


