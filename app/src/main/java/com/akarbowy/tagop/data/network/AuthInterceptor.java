package com.akarbowy.tagop.data.network;

import com.akarbowy.tagop.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String appKeyPath = "appkey/" + BuildConfig.API_KEY;
        HttpUrl authorizedUrl = original.url().newBuilder()
                .addPathSegments(appKeyPath)
                .build();

        Request authorized = original.newBuilder()
                .url(authorizedUrl)
                .build();

        return chain.proceed(authorized);
    }
}
