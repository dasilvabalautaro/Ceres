package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import javax.inject.Inject
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemoteGet
import com.google.gson.Gson
import org.json.JSONArray

class UrlVideoGetUseCase @Inject constructor(serviceRemoteGet:
                                             ServiceRemoteGet):
        RequestGetUseCase(serviceRemoteGet) {

    init {
        observableMessage
                .subscribe { messageError }
    }

    override fun getJsonArray(messageOfService: MessageOfService) {
        val response = Gson()
        if (messageOfService.success){
            val result = response.toJson(messageOfService.result)

            if (!result.isEmpty()){
                try {
                    val jsonArray =  JSONArray(result)
                    println(jsonArray.toString(4))
                }catch (e: Throwable){
                    println(e.message)
                }
            }

        }else{
            val responseError = response.toJson(messageOfService.error)
            this.messageError =  responseError.toString()
            this.observableMessage.onNext(this.messageError)
        }
    }

    override fun sendMessageError(message: String) {
        this.messageError =  message
        this.observableMessage.onNext(this.messageError)
    }

}