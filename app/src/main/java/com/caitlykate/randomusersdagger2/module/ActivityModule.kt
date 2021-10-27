package com.caitlykate.randomusersdagger2.module

import com.caitlykate.randomusersdagger2.interfaces.RandomUserApplicationScope

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ActivityModule (val context: Activity) {

    @Named("activity_context")
    @RandomUserApplicationScope
    @Provides
    fun provideContext(): Context {
        return context
    }

}