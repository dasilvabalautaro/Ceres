package com.globalhiddenodds.ceres.dagger

import android.content.Context
import com.globalhiddenodds.ceres.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)
    fun context(): Context
    fun plus(presenterModule: PresenterModule): PresenterComponent
}