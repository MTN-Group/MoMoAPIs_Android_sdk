package com.momo.sdk.network;

import androidx.annotation.NonNull;

import com.momo.sdk.MomoApplication;
import com.momo.sdk.network.APIService;
import com.momo.sdk.network.NetworkConnectionInterceptor;
import com.momo.sdk.network.NullOnEmptyConverterFactory;
import com.momo.sdk.util.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static Retrofit retrofit;
    private static APIService helper;

    /**
     * Gets api helper.
     *
     * @return the api helper
     */

    @NonNull
    public static APIService getApiHelper(){
        OkHttpClient okHttpClient = getOkHttpClient();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(NullOnEmptyConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }



        if (helper == null) {
            helper = retrofit.create(APIService.class);
        }

        return helper;
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.authenticator(new Authenticator());
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClientBuilder.addInterceptor(new NetworkConnectionInterceptor(MomoApplication.getAppContext()));

        return httpClientBuilder.build();
    }


}
