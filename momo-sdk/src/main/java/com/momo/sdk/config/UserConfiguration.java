package com.momo.sdk.config;

//configuration class for user
@SuppressWarnings("unused")
public class UserConfiguration {
    @SuppressWarnings("FieldCanBeLocal")
    private final String subscriptionKey;

    @SuppressWarnings("AccessStaticViaInstance")
    public UserConfiguration(UserConfigurationBuilder userConfigurationBuilder) {
        this.subscriptionKey =userConfigurationBuilder.subscriptionKey;
    }

    //Builder class for user
    @SuppressWarnings("AccessStaticViaInstance")
    public static class UserConfigurationBuilder {
        private static String subscriptionKey;

        public UserConfiguration.UserConfigurationBuilder setSubscriptionKey(String subscriptionKey) {
            this.subscriptionKey = subscriptionKey;
            return this;
        }
        public  static String getSubscriptionKey() {
            return subscriptionKey;
        }
        public UserConfiguration build(){
            return new UserConfiguration(this);
        }
    }
}
