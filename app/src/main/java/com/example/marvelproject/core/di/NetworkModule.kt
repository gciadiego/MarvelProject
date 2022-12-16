package com.example.marvelproject.core.di

import com.example.marvelproject.BuildConfig
import com.example.marvelproject.data.retrofit.IMarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val ts = Timestamp(System.currentTimeMillis()).time.toString()
                val input = "$ts${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}"
                val md = MessageDigest.getInstance("MD5")
                val hash = BigInteger(1, md.digest(input.toByteArray()))
                    .toString(16)
                    .padStart(32, '0')

                val request = chain.request()
                val url = request.url

                val newUrl = url.newBuilder().apply {
                    this.addQueryParameter("ts", ts)
                    this.addQueryParameter("hash", hash)
                    this.addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
                    this.addQueryParameter("limit","100")
                }.build()

                val newRequest = request.newBuilder().url(newUrl).build()

                chain.proceed(newRequest)
            }
            .addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) )
            .readTimeout(10L,TimeUnit.SECONDS)
            .writeTimeout(10L,TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient): Retrofit =
        Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesDrinkApi(retrofit: Retrofit): IMarvelApi =
        retrofit.create(IMarvelApi::class.java)
}