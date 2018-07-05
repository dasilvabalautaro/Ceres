package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.interfaces.IServicePost
import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemotePost
import com.globalhiddenodds.ceres.tools.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException

abstract class RequestPostUseCase constructor(serviceRemotePost:
                                              ServiceRemotePost){
    private var servicePost: IServicePost? = null
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var messageError: String = ""
    var observableMessage: Subject<String> = PublishSubject.create()
    private var body: RequestBody? = null
    private var service: String = ""


    init {
        observableMessage
                .subscribe { messageError }
        try {
            this.servicePost = serviceRemotePost.getService()
        }catch (ie: IllegalArgumentException){
            println(ie.message)
        }
    }

    fun createBody(bodyPost: Map<String, Any>, urlService: String): Boolean{
        this.service = urlService
        this.body = createRequestBody(bodyPost)
        return this.body != null
    }

    private fun createRequestBody(bodyPost: Map<String, Any>): RequestBody?{
        try {
            return RequestBody.create(
                    MediaType.parse(Constants.content_type),
                    (JSONObject(bodyPost).toString())
            )

        }catch (je: JSONException){
            println(je.message)
        }
        return null
    }

    fun getDataServer(){
        if (servicePost != null){
            try {
                disposable.add(servicePost!!.sendPost("",
                        this.service,
                        body!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getMessageService())
                )

            }catch (se: SocketTimeoutException){
                println(se.message)
            }

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
                println(e.message)
            }
        }
    }

    protected abstract  fun getJsonArray(messageOfService: MessageOfService)
    protected fun sendMessageError(message: String){
        this.messageError =  message
        this.observableMessage.onNext(this.messageError)
    }
}