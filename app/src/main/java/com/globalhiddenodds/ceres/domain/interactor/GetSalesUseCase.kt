package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemotePost
import com.globalhiddenodds.ceres.presentation.data.SaleModel
import com.google.gson.*
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class GetSalesUseCase @Inject constructor(serviceRemotePost:
                                          ServiceRemotePost):
        RequestPostUseCase(serviceRemotePost){

    private val listSaleModel: ArrayList<SaleModel> = ArrayList()
    var observableEndTask: Subject<List<SaleModel>> = PublishSubject.create()

    init {
        observableEndTask
                .subscribe { listSaleModel }
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
            listSaleModel.clear()
            (0 until jsonArray.size()).forEach { i ->

                val objJson: JsonObject = jsonArray.get(i).asJsonObject
                val saleModel = SaleModel()

                for (entry in objJson.entrySet()) {
                    if (entry.key == "id"){
                        saleModel.id = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "product"){
                        saleModel.product = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "date_sale"){
                        saleModel.date = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "weight"){
                        saleModel.weight = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "client"){
                        saleModel.client = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "price"){
                        saleModel.price = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "operator"){
                        saleModel.operator = (entry.value as JsonElement).asString?:""
                    }
                    if (entry.key == "total"){
                        saleModel.mount = (entry.value as JsonElement).asString?:""
                    }

                }

                listSaleModel.add(saleModel)
            }
            observableEndTask.onNext(listSaleModel.toList())
        }
    }

}