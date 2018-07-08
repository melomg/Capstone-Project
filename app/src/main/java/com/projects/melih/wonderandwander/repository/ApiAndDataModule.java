package com.projects.melih.wonderandwander.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.projects.melih.wonderandwander.BuildConfig;
import com.projects.melih.wonderandwander.repository.local.LocalCityDataSource;
import com.projects.melih.wonderandwander.repository.local.LocalUserDataSource;
import com.projects.melih.wonderandwander.repository.local.WonderAndWanderDatabase;
import com.projects.melih.wonderandwander.repository.remote.WonderAndWanderService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@SuppressWarnings("unused")
@Module
public class ApiAndDataModule {
    private static final long TIMEOUT_SECOND = 60;

    @Singleton
    @Provides
    LocalCityDataSource provideLocalCityDataSource(@NonNull WonderAndWanderDatabase database) {
        return new LocalCityDataSource(database.cityDao());
    }

    @Singleton
    @Provides
    LocalUserDataSource provideLocalUserDataSource(@NonNull WonderAndWanderDatabase database) {
        return new LocalUserDataSource(database.userDao());
    }

    @Singleton
    @Provides
    WonderAndWanderDatabase provideDatabase(@NonNull Context context) {
        return WonderAndWanderDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    WonderAndWanderService provideServices(@NonNull Retrofit retrofit) {
        return retrofit.create(WonderAndWanderService.class);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(@NonNull HttpLoggingInterceptor httpLoggingInterceptor, @Nullable StethoInterceptor stethoInterceptor, @NonNull List<Interceptor> defaultInterceptors) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS);

        if (stethoInterceptor != null) {
            httpClientBuilder.addNetworkInterceptor(stethoInterceptor);
        }

        for (Interceptor interceptor : defaultInterceptors) {
            httpClientBuilder.addInterceptor(interceptor);
        }

        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpClientBuilder.addInterceptor(httpLoggingInterceptor);

        return httpClientBuilder.build();
    }

    @Singleton
    @Provides
    List<Interceptor> provideDefaultInterceptors() {
        return new ArrayList<>();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Singleton
    @Provides
    StethoInterceptor provideStethoInterceptor() {
        if (BuildConfig.DEBUG) {
            return new StethoInterceptor();
        }
        return null;
    }
}