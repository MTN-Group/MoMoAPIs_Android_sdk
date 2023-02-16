
# Get user details 

`Here, getUserDetails() creates a GET request to apiuser/{referenceId}`

> `This endpoint allows momo user to get the details of created user`

### Usage/Examples

Pass the reference id obtained from the createUser() function into getUserDetails() to get the details of the user



```java


  SDKManager.authentication.getUserDetails(userReferenceId, new UserDetailInterface() {
            @Override
            public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

            }

            @Override
            public void onUserDetailInterFaceFailure(MtnError mtnError) {
               
            }
        });


```
### Example Output

```json
 {
 	"providerCallbackHost": "webhook.site",
 	"targetEnvironment": "sandbox"
 }

```


