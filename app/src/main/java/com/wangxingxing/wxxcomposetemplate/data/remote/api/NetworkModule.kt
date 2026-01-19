package com.wangxingxing.wxxcomposetemplate.data.remote.api

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wangxingxing.wxxcomposetemplate.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 网络模块配置
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.example.com/"
    private const val WAN_ANDROID_BASE_URL = "https://www.wanandroid.com/"
    private const val BING_WALLPAPER_BASE_URL = "https://cn.bing.com/"
    private const val LOCAL_BASE_URL = "http://192.168.10.254:8080/api/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(App.instance))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * 提供 wanandroid API 服务
     */
    @Provides
    @Singleton
    fun provideWanAndroidApiService(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): WanAndroidApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(WAN_ANDROID_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(WanAndroidApiService::class.java)
    }
    
    /**
     * 提供必应壁纸服务
     * 直接创建 Retrofit 实例并返回服务
     */
    @Provides
    @Singleton
    fun provideBingWallpaperService(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): BingWallpaperService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BING_WALLPAPER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(BingWallpaperService::class.java)
    }

    /**
     * 提供本地局域网 API 服务
     */
    @Provides
    @Singleton
    @Named("local")
    fun provideLocalRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LOCAL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalApiService(@Named("local") retrofit: Retrofit): LocalApiService {
        return retrofit.create(LocalApiService::class.java)
    }
}
