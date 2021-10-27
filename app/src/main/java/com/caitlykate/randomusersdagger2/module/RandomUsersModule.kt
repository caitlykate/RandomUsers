package com.caitlykate.randomusersdagger2.module

import com.caitlykate.randomusersdagger2.interfaces.RandomUserApplicationScope
import com.caitlykate.randomusersdagger2.interfaces.RandomUsersApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [OkHttpClientModule::class])
class RandomUsersModule {

    @Provides
    fun provideRandomUsersApi(retrofit: Retrofit): RandomUsersApi{
        return retrofit.create(RandomUsersApi::class.java)
    }

    @RandomUserApplicationScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory,
                        gson: Gson): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory{
        return  GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideGson(): Gson{
        return GsonBuilder().create()
    }
}