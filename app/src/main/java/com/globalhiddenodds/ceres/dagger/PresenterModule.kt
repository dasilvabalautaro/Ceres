package com.globalhiddenodds.ceres.dagger

import com.globalhiddenodds.ceres.domain.UIThread
import com.globalhiddenodds.ceres.domain.interactor.GetPaysUseCase
import com.globalhiddenodds.ceres.domain.interactor.GetSalesUseCase
import com.globalhiddenodds.ceres.domain.interactor.UrlVideoGetUseCase
import com.globalhiddenodds.ceres.domain.interactor.VerifyLoginUseCase
import com.globalhiddenodds.ceres.models.executor.JobExecutor
import com.globalhiddenodds.ceres.models.interfaces.IPostExecutionThread
import com.globalhiddenodds.ceres.models.interfaces.IThreadExecutor
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemoteGet
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemotePost
import com.globalhiddenodds.ceres.presentation.presenter.GetPaysPresenter
import com.globalhiddenodds.ceres.presentation.presenter.GetSalesPresenter
import com.globalhiddenodds.ceres.presentation.presenter.LoginPresenter
import com.globalhiddenodds.ceres.presentation.presenter.UrlVideoPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideJobExecutor(): JobExecutor{
        return JobExecutor()
    }

    @Provides
    fun provideThreadExecutor(jobExecutor: JobExecutor): IThreadExecutor {
        return jobExecutor
    }

    @Provides
    fun provideUIThread(): UIThread{
        return UIThread()
    }
    @Provides
    fun providePostExecutionThread(uiThread: UIThread): IPostExecutionThread {
        return uiThread
    }

    @Provides
    fun provideServiceRemotePost(): ServiceRemotePost {
        return ServiceRemotePost()
    }

    @Provides
    fun provideServiceRemoteGet(): ServiceRemoteGet {
        return ServiceRemoteGet()
    }

    @Provides
    fun provideVerifyLoginUseCase(serviceRemotePost:
                                  ServiceRemotePost): VerifyLoginUseCase{
        return VerifyLoginUseCase(serviceRemotePost)
    }

    @Provides
    fun provideGetSalesUseCase(serviceRemotePost:
                               ServiceRemotePost): GetSalesUseCase{
        return GetSalesUseCase(serviceRemotePost)
    }

    @Provides
    fun provideGetPaysUseCase(serviceRemotePost:
                              ServiceRemotePost): GetPaysUseCase{
        return GetPaysUseCase(serviceRemotePost)
    }

    @Provides
    fun provideGetPaysPresenter(getPaysUseCase:
                                GetPaysUseCase): GetPaysPresenter{
        return GetPaysPresenter(getPaysUseCase)
    }

    @Provides
    fun provideGetSalesPresenter(getSalesUseCase:
                                 GetSalesUseCase): GetSalesPresenter{
        return GetSalesPresenter(getSalesUseCase)
    }

    @Provides
    fun provideLoginPresenter(verifyLoginUseCase:
                              VerifyLoginUseCase): LoginPresenter{
        return LoginPresenter(verifyLoginUseCase)
    }

    @Provides
    fun provideUrlVideoGetUseCase(serviceRemoteGet:
                                  ServiceRemoteGet): UrlVideoGetUseCase{
        return UrlVideoGetUseCase(serviceRemoteGet)
    }

    @Provides
    fun provideUrlVideoPresenter(urlVideoGetUseCase:
                                 UrlVideoGetUseCase): UrlVideoPresenter{
        return UrlVideoPresenter(urlVideoGetUseCase)
    }
}