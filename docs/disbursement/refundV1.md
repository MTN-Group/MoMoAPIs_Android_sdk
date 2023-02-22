
# Refund v1 

`Here, refundV1() creates a POST request to disbursement/v1_0/refund`

> `refund operation is used to refund an amount from the owner’s account to a payee account.`

Construct a model class and set desired parameter

### Usage/Examples

```java
        Refund refund = new Refund();

        refund.setAmount("5.0");
        refund.setCurrency("EUR");
        refund.setExternalId("6353636");
        refund.setPayerMessage("Pay for product a");
        refund.setPayeeNote("payer note");
        refund.setReferenceIdToRefund("0c0649fc-d3d0-43e7-94c1-5dab1637098a");
```

```java

         SDKManager.disbursement.refundV1(refund, "<callbackUrl>", new RequestInterface() {
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


