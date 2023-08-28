package com.weatherapptask.koin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weatherapptask.BuildConfig
import com.weatherapptask.network.weatherForcast.WeatherApi
import com.weatherapptask.network.weatherForcast.WeatherForecastRepository
import com.weatherapptask.utilits.checkNetwork.AppConnectionMonitor
import com.weatherapptask.utilits.checkNetwork.ConnectivityInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val ConnectionMonitorModule = module {
    single { AppConnectionMonitor(get()) }

}

val InterceptorModule = module {
    factory { ConnectivityInterceptor(get(), get()) }
}

val OkHttpModule = module {
    factory { provideGson() }
    single(named("BaseHttpClient")) { provideBaseHttpClient(get()) }
}

val RetrofitModule = module {
    single(named("BaseRetrofit")) {
        provideConsumerRetrofit(get(named("BaseHttpClient")), get())
    }
}

val WeatherForcastApiModule = module {
    factory { provideWeatherForeCastApi(get(named("BaseRetrofit"))) }
    factory { WeatherForecastRepository.create(get()) }
}

fun provideWeatherForeCastApi(manager: Retrofit): WeatherApi =
    manager.create(WeatherApi::class.java)



fun provideGson(): Gson {
    return GsonBuilder()
        .setLenient()
        .create()
}

fun provideConsumerRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BuildConfig.BASE_SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}


fun provideBaseHttpClient(
    connectivityInterceptor: ConnectivityInterceptor,
): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(connectivityInterceptor)
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
    }
    clientBuilder.connectTimeout(60, TimeUnit.SECONDS)
    return clientBuilder.build()
}


val networkComponent = listOf(
    InterceptorModule,
    OkHttpModule,
    ConnectionMonitorModule,
    RetrofitModule,
    WeatherForcastApiModule,
)


