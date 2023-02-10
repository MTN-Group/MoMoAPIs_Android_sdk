package com.momo.sdk.network;


import androidx.annotation.NonNull;

import com.momo.sdk.model.StatusResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class NullOnEmptyConverterFactory extends Converter.Factory {
    private NullOnEmptyConverterFactory() {
    }

    public static Converter.Factory create() {
        return new NullOnEmptyConverterFactory();
    }

    @NonNull
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return (Converter<ResponseBody, Object>) body -> {
            if (body.contentLength() == 0) {
                StatusResponse statusResponse=new StatusResponse();
                statusResponse.setStatus("true");
                return statusResponse;
            }
            return delegate.convert(body);
        };
    }
}