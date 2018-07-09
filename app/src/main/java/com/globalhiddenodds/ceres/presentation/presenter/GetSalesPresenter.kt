package com.globalhiddenodds.ceres.presentation.presenter

import com.globalhiddenodds.ceres.domain.interactor.GetSalesUseCase
import com.globalhiddenodds.ceres.tools.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GetSalesPresenter @Inject constructor(private val getSalesUseCase:
                                            GetSalesUseCase): BasePresenter(){

    init {
        val message = this.getSalesUseCase.observableMessage.map { s -> s }
        disposable.add(message.observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    kotlin.run {
                        view!!.showError(s)
                    }
                })
        val userMain = this.getSalesUseCase.observableEndTask.map { u -> u }
        disposable.add(userMain.observeOn(AndroidSchedulers.mainThread())
                .subscribe { u ->
                    kotlin.run {
                        view!!.executeTask(u)
                    }
                })
    }

    fun executeGetList(bodyPost: Map<String, Any>){
        if (this.getSalesUseCase.createBody(bodyPost,
                        Constants.service_get_sales_day)){
            this.getSalesUseCase.getDataServer()
        }

    }

}