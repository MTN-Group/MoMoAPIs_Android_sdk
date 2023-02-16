
# Create User

`Here, createUser() creates a POST request to /apiuser`

> `This endpoint allows momo user to generate a user in the API manager portal`

### Usage/Examples

Construct a callback request model and set desired paramaters

```java
        CallBackHost callBackHost = new CallBackHost();
        callBackHost.setProviderCallbackHost("<place your callback host url>");

```

```java


     SDKManager.authentication.createUser(callBackHost, new RequestInterface() {
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
202
{
  "serverCorrelationId": "dae8ef64-4340-40b4-863e-ddbe9d63374b",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "1056",
  "pollLimit": 100
}
```


