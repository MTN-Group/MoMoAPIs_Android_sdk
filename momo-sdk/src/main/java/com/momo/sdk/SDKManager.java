package com.momo.sdk;

import com.momo.sdk.controller.Authentication;

public class SDKManager {

    public static final Authentication authentication=new Authentication();


    private SDKManager() {
    }

    @SuppressWarnings("unused")
    public static SDKManager getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    @SuppressWarnings({"unused", "InstantiationOfUtilityClass"})
    private static class SingletonCreationAdmin {
        private static final SDKManager INSTANCE = new SDKManager();
    }
}


