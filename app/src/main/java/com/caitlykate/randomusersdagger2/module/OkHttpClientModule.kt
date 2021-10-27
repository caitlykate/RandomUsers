package com.caitlykate.randomusersdagger2.module

import android.content.Context
import com.caitlykate.randomusersdagger2.interfaces.ApplicationContext
import com.caitlykate.randomusersdagger2.interfaces.RandomUserApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import javax.inject.Named

@Module(includes = [ContextModule::class])
class OkHttpClientModule {

    @Provides
    fun provideOkHttpClient(cache: Cache,
                            httpLoggingInterceptor: HttpLoggingInterceptor)
        :OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideCache(cacheFile: File): Cache{
        return Cache(cacheFile, 10 * 1000 * 1000)
    }

    @Provides
    @RandomUserApplicationScope
    fun provideFile( @ApplicationContext context: Context): File{
        val cacheFile = File(context.cacheDir, "HttpCache")
        cacheFile.mkdirs()
        return cacheFile
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}