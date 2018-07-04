package com.globalhiddenodds.ceres.dagger

import android.content.Context
import com.globalhiddenodds.ceres.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return this.app
    }

}