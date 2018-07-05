package com.globalhiddenodds.ceres.presentation.presenter

import com.globalhiddenodds.ceres.domain.interactor.VerifyLoginUseCase
import com.globalhiddenodds.ceres.tools.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val verifyLoginUseCase:
                                         VerifyLoginUseCase): BasePresenter(){
    init {
        val message = this.verifyLoginUseCase.observableMessage.map { s -> s }
        disposable.add(message.observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    kotlin.run {
                        view!!.showError(s)
                    }
                })
        val userMain = this.verifyLoginUseCase.observableEndTask.map { u -> u }
        disposable.add(userMain.observeOn(AndroidSchedulers.mainThread())
                .subscribe { u ->
                    kotlin.run {
                        view!!.executeTask(u)
                    }
                })
    }

    fun executeVerifyLogin(bodyPost: Map<String, Any>){
        if (this.verifyLoginUseCase.createBody(bodyPost,
                        Constants.service_verify_login)){
            this.verifyLoginUseCase.getDataServer()
        }

    }

}