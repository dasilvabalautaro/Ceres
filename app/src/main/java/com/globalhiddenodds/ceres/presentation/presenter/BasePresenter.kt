package com.globalhiddenodds.ceres.presentation.presenter

import com.globalhiddenodds.ceres.presentation.interfaces.ILoadDataView
import com.globalhiddenodds.ceres.presentation.interfaces.IPresenter
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter: IPresenter {
    protected var disposable: CompositeDisposable = CompositeDisposable()
    var view: ILoadDataView? = null

    override fun destroy() {
        this.view = null
        if (!this.disposable.isDisposed ) this.disposable.dispose()
    }

    override fun showMessage(message: String) {
        this.view!!.showMessage(message)
    }

    override fun showError(error: String) {
        this.view!!.showError(error)
    }

}