package com.caitlykate.randomusersdagger2.module

import android.content.Context
import com.caitlykate.randomusersdagger2.interfaces.ApplicationContext
import com.caitlykate.randomusersdagger2.interfaces.RandomUserApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ContextModule(val context: Context) {

    @ApplicationContext
    @RandomUserApplicationScope
    @Provides
    fun provideContext(): Context{
        return context.applicationContext
    }
}