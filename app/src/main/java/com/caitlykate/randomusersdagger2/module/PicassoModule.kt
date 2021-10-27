package com.caitlykate.randomusersdagger2.module

import android.content.Context
import com.caitlykate.randomusersdagger2.interfaces.ApplicationContext
import com.caitlykate.randomusersdagger2.interfaces.RandomUserApplicationScope
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Named

@Module(includes = [OkHttpClientModule::class])
class PicassoModule {

    @RandomUserApplicationScope
    @Provides
    fun providePicasso(@ApplicationContext context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso{
        return Picasso.Builder(context)
            .downloader(okHttp3Downloader)
            .build()
    }

    @Provides
    fun provideOkHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader{
        return OkHttp3Downloader(okHttpClient)
    }
}