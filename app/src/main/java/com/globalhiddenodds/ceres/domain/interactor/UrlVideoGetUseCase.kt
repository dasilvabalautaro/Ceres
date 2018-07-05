package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import javax.inject.Inject
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemoteGet
import com.google.gson.*

class UrlVideoGetUseCase @Inject constructor(serviceRemoteGet:
                                             ServiceRemoteGet):
        RequestGetUseCase(serviceRemoteGet) {

    override fun getJsonArray(messageOfService: MessageOfService) {
        val response = Gson()
        if (messageOfService.success){
            val result = response.toJson(messageOfService.result)

            if (!result.isEmpty()){
                try {
                    val objJson = JsonParser().parse(result)
                    if (objJson.isJsonObject){
                        println((objJson.asJsonObject).get("operation"))
                    }
                    if (objJson.isJsonArray){
                        println(objJson.asJsonArray.toString())
                    }

                }catch (e: Throwable){
                    println(e.message)
                }
            }

        }else{
            val responseError = response.toJson(messageOfService.error)
            sendMessageError(responseError.toString())

        }
    }


}