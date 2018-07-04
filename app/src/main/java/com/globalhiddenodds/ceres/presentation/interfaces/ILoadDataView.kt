package com.globalhiddenodds.ceres.presentation.interfaces

interface ILoadDataView {
    fun showMessage(message: String)
    fun showError(message: String)
    fun <T> executeTask(obj: T)
    fun <T> executeTask(objList: List<T>)
}