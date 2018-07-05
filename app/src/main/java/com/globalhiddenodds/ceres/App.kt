package com.globalhiddenodds.ceres

import android.app.Application
import com.globalhiddenodds.ceres.dagger.AppComponent
import com.globalhiddenodds.ceres.dagger.AppModule
import com.globalhiddenodds.ceres.dagger.DaggerAppComponent
import com.globalhiddenodds.ceres.models.persistent.network.ConnectionNetwork
import javax.inject.Inject

class App: Application()  {
    @Inject
    lateinit var connectionNetwork: ConnectionNetwork

    companion object{
        lateinit var appComponent: AppComponent

    }


    private val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

    }

    fun getAppComponent(): AppComponent{
        appComponent = component
        return appComponent
    }


}