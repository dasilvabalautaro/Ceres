package com.globalhiddenodds.ceres.presentation.interfaces

interface IPresenter {
    fun destroy()
    fun showMessage(message: String)
    fun showError(error: String)
}