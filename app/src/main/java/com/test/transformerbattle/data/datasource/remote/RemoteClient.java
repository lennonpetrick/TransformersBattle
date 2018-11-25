package com.test.transformerbattle.data.datasource.remote;

import android.support.annotation.NonNull;

import com.test.transformerbattle.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteClient {

    private static RemoteClient INSTANCE;

    private Retrofit mRetrofit;

    public static RemoteClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteClient();
        }

        return INSTANCE;
    }

    private RemoteClient() {
        this.mRetrofit = createRetrofit();
    }

    public <T> T createService(@NonNull Class<T> service) {
        return mRetrofit.create(service);
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BaseUrls.TRANSFORMERS)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createHttpClient())
                .build();
    }

    private OkHttpClient createHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(createLogInterceptor());
        return httpClient.build();
    }

    private Interceptor createLogInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

}
