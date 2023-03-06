
# Delivery notification 

`Here, requestToPayDeliveryNotification() creates a POST request to remittance/v1_0/requesttopay/{referenceId}/deliverynotification`

> `This operation is used to send additional Notification to an End User.`

### Usage/Examples

```java

    SDKManager.remittance.requestToPayTransactionStatus(requestReferenceId, new RequestPayStatusInterface() {
            @Override
            public void onRequestStatusSuccess(RequestPayStatus requestPayStatus) {
            
            }

            @Override
            public void onRequestStatusFailure(MtnError mtnError) {

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
