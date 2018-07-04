package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.interfaces.IServiceGet
import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemoteGet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class RequestGetUseCase constructor(private val serviceRemoteGet:
                                             ServiceRemoteGet) {
    private var serviceGet: IServiceGet? = null
    protected var disposable: CompositeDisposable = CompositeDisposable()
    var messageError: String = ""
    var observableMessage: Subject<String> = PublishSubject.create()

    init {
        try {
            this.serviceGet = serviceRemoteGet.getService()
        }catch (ie: IllegalArgumentException){
            println(ie.message)
        }

    }

    fun executeRequestGet(head: String, urlService: String){
        if (serviceGet != null){
            disposable.add(serviceGet!!.sendGet(head,
                    urlService)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(getMessageService())
            )
        }
    }

    private fun getMessageService(): DisposableSingleObserver<MessageOfService> {
        return object : DisposableSingleObserver<MessageOfService>() {
            override fun onSuccess(value: MessageOfService) {
                getJsonArray(value)
            }

            override fun onError(e: Throwable) {
                if (!e.message.isNullOrEmpty()){
                    sendMessageError(e.message!!)
                }
            }
        }
    }

    protected abstract  fun getJsonArray(messageOfService: MessageOfService)
    protected abstract fun sendMessageError(message: String)

}