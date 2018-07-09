package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemotePost
import com.globalhiddenodds.ceres.presentation.data.PayModel
import com.google.gson.*
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class GetPaysUseCase @Inject constructor(serviceRemotePost:
                                         ServiceRemotePost):
        RequestPostUseCase(serviceRemotePost){

    private val listPayModel: ArrayList<PayModel> = ArrayList()
    var observableEndTask: Subject<List<PayModel>> = PublishSubject.create()

    init {
        observableEndTask
                .subscribe { listPayModel }
    }


    override fun getJsonArray(messageOfService: MessageOfService) {
        val response = Gson()
        if (messageOfService.success){
            val result = response.toJson(messageOfService.result)

            if (!result.isEmpty()){
                try {
                    val objJson = JsonParser().parse(result)
                    if (objJson.isJsonObject){
                        println(objJson.asJsonObject.toString())

                    }
                    if (objJson.isJsonArray){
                        getList(objJson.asJsonArray)
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

    private fun getList(jsonArray: JsonArray){
        if (jsonArray.size() != 0){
            listPayModel.clear()
            (0 until jsonArray.size()).forEach { i ->

                val objJson: JsonObject = jsonArray.get(i).asJsonObject
                val payModel = PayModel()

                for (entry in objJson.entrySet()) {
                    if (entry.key == "id"){
                        payModel.id = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "date_pay"){
                        payModel.date = (entry.value as JsonElement).asString?:""
                    }

                    if (entry.key == "client"){
                        payModel.client = (entry.value as JsonElement).asString?:""
                    }

                    if (entry.key == "operator"){
                        payModel.operator = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "mount"){
                        payModel.mount = (entry.value as JsonElement).asString?:""
                    }

                }

                listPayModel.add(payModel)
            }
            observableEndTask.onNext(listPayModel.toList())
        }
    }

}