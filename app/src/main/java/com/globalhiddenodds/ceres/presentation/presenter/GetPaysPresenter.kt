package com.globalhiddenodds.ceres.presentation.presenter

import com.globalhiddenodds.ceres.domain.interactor.GetPaysUseCase
import com.globalhiddenodds.ceres.tools.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GetPaysPresenter @Inject constructor(private val getPaysUseCase:
                                           GetPaysUseCase): BasePresenter() {

    init {
        val message = this.getPaysUseCase.observableMessage.map { s -> s }
        disposable.add(message.observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    kotlin.run {
                        view!!.showError(s)
                    }
                })
        val userMain = this.getPaysUseCase.observableEndTask.map { u -> u }
        disposable.add(userMain.observeOn(AndroidSchedulers.mainThread())
                .subscribe { u ->
                    kotlin.run {
                        view!!.executeTask(u)
                    }
                })
    }

    fun executeGetList(bodyPost: Map<String, Any>){
        if (this.getPaysUseCase.createBody(bodyPost,
                        Constants.service_get_pays_day)){
            this.getPaysUseCase.getDataServer()
        }

    }

}