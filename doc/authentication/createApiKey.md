
# Create Api Key

`Here, createApiKey() creates a POST request to apiuser/{referenceId}/apikey`

> `This endpoint allows momo user to create a api key in API manager portal`

### Usage/Examples

Pass the reference id of created user into createApiKey() to get the api key

```java

 SDKManager.authentication.createApiKey(userReferenceId, new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
             
            }
            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
             
            }
        });


```
### Example Output

```json
{
	"apiKey": "3c1f360a71474f2faea859a671a6caef"
}
```


