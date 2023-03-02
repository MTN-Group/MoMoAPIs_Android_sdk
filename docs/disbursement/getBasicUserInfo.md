
# Get Basic User info

`Here, getBasicUserInfo() creates a POST request to disbursement/v1_0/accountholder/msisdn/{accountHolderMSISDN}/basicuserinfo`

> `This operation returns personal information of the account holder. The operation does not need any consent by the account holder`


### Usage/Examples


```java

    SDKManager.disbursement.getBasicUserInfo(accountIdentifier, new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {
            
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
	"updated_at": 1676609351
}

```


