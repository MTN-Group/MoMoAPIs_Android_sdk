
# Create User

`Here, createApiKey() creates a POST request to /apiuser`

> `This endpoint allows momo user to generate a api key in the API manager portal`

### Usage/Examples

Pass the reference id of created user to get the api key of a user


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
{
  "status": "true"

}
```


