package com.globalhiddenodds.ceres.presentation.presenter

import com.globalhiddenodds.ceres.domain.interactor.UrlVideoGetUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UrlVideoPresenter @Inject constructor(private val urlVideoGetUseCase:
                                            UrlVideoGetUseCase): BasePresenter(){
    init {
        val message = this.urlVideoGetUseCase.observableMessage.map { s -> s }
        disposable.add(message.observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    kotlin.run {
                        view!!.showError(s)
                    }
                })
    }

    fun getUrlVideo(){
        this.urlVideoGetUseCase.executeRequestGet("",
                "http://www.globalhiddenodds.com/index.php/test")
    }
}