package com.example.marvelproject.core.di

import com.example.marvelproject.BuildConfig
import com.example.marvelproject.data.implementation.MarvelApiRepository
import com.example.marvelproject.data.retrofit.IMarvelApi
import com.example.marvelproject.domain.interfaces.IApiRepository
import com.example.marvelproject.presentation.view.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp


//Defining API Module
val apiModule = module {
    fun provideApi(retrofit: Retrofit): IMarvelApi {
        return retrofit.create(IMarvelApi::class.java)
    }
    single { provideApi(get()) }
}

//Defining network module
val networkModule = module {
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
            .build()

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    }

    single { provideOkHttp()}
    single {
        provideRetrofit(get())
    }
}

//Defining the repository module
val repositoryModule = module {
    fun provideRepository(api: IMarvelApi): IApiRepository {
        return MarvelApiRepository(api)
    }
    single { provideRepository(get()) }
}

//Defining ViewModel module with Koin
val vmModule = module {
    singleOf(::MarvelApiRepository) { bind<IApiRepository>()}
    viewModel { MainViewModel(get()) }
}


