package com.example.alizee.starwarspriv.core.di

import android.app.Application
import com.example.alizee.starwarspriv.core.BASE_URL
import com.example.alizee.starwarspriv.core.utils.AppDateHelper
import com.example.alizee.starwarspriv.core.utils.AppUnitFormatter
import com.example.alizee.starwarspriv.core.utils.DateHelper
import com.example.alizee.starwarspriv.core.utils.UnitFormatter
import com.example.alizee.starwarspriv.details.data.network.TripDetailApi
import com.example.alizee.starwarspriv.details.domain.TripDetailTransformer
import com.example.alizee.starwarspriv.travelsList.data.network.TravelListApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext() = application.applicationContext

    @MainThreadScheduler
    @Provides
    @Singleton
    internal fun provideMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @IoScheduler
    @Provides
    @Singleton
    internal fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(StethoInterceptor())
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
            chain.proceed(request)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Singleton
    fun provideTravelListApi(retrofit: Retrofit): TravelListApi {
        return retrofit.create(TravelListApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTripDetailApi(retrofit: Retrofit): TripDetailApi {
        return retrofit.create(TripDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDateHelper(): DateHelper = AppDateHelper()

    @Provides
    @Singleton
    fun provideUnitFormatter(): UnitFormatter = AppUnitFormatter()

    @Provides
    @Singleton
    fun provideTripDetailTransformer(dateHelper: DateHelper, unitFormatter: UnitFormatter) =
        TripDetailTransformer(dateHelper, unitFormatter)
}