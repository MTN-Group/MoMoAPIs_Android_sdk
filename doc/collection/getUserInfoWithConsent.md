
# Get UserInfo WithConsent

`Here, getUserInfoWithConsent() creates a POST request to collection/oauth2/v1_0/userinfo`

> `This operation is used to claim a consent by the account holder for the requested scopes.`


### Usage/Examples

Construct a account holder object and set desired values

```java
        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");

```

```java

 SDKManager.collection.getUserInfoWithConsent(accountHolder,AccessType.offline, "profile", new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {
            
            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {

            }
        });
```


### Example Output

```json
 {
 	"sub": "0",
 	"name": "Sand Box",
 	"given_name": "Sand",
 	"family_name": "Box",
 	"birthdate": "1976-08-13",
 	"locale": "sv_SE",
 	"gender": "MALE",
 	"updated_at": 1676961048
 }

```


