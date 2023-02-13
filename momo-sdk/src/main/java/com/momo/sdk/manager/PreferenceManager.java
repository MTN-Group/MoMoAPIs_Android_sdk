package com.momo.sdk.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.momo.sdk.util.SubscriptionType;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * store the application data
 */

@SuppressWarnings("unused")
public class PreferenceManager {

    private SharedPreferences preferences;

    /**
     * @return PreferenceManager singleton instance of the class
     */
    @SuppressWarnings("unused")
    public static PreferenceManager getInstance() {
        return SingleInstanceAdmin.instance;
    }

    /**
     * @param context Context of the application
     */

    @SuppressWarnings("unused")
    @SuppressLint("ObsoleteSdkInt")
    public void init(Context context) {
        if (preferences == null) {
            preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            try {
                MasterKey masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
                preferences = EncryptedSharedPreferences.create(
                        context,
                        "secret_shared_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * @param token save the token to shared preferences
     *
     */
    @SuppressWarnings("unused")
    public void saveToken(String token, SubscriptionType subscriptionType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(subscriptionType.name(), token);
        editor.apply();
    }

    /**
     * @return retrieve the access token
     */

    @SuppressWarnings("unused")
    public String retrieveToken(SubscriptionType subscriptionType) {
        if (preferences == null) {
            return "";
        } else {
            return preferences.getString(subscriptionType.name(), "");
        }
    }

    /**
     * creating singleton instance of the class
     */
    @SuppressWarnings("unused")
    private static class SingleInstanceAdmin {
        static final PreferenceManager instance = new PreferenceManager();
    }

}
