package com.globalhiddenodds.ceres.domain.interactor

import com.globalhiddenodds.ceres.models.persistent.network.MessageOfService
import com.globalhiddenodds.ceres.models.persistent.network.ServiceRemotePost
import com.globalhiddenodds.ceres.presentation.data.UserModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject
import com.google.gson.JsonElement

class VerifyLoginUseCase @Inject constructor(serviceRemotePost:
                                             ServiceRemotePost):
        RequestPostUseCase(serviceRemotePost) {

    private var user: UserModel = UserModel()
    var observableEndTask: Subject<UserModel> = PublishSubject.create()

    init {
        observableEndTask
                .subscribe { user }
    }

    override fun getJsonArray(messageOfService: MessageOfService) {
        val response = Gson()
        if (messageOfService.success){
            val result = response.toJson(messageOfService.result)

            if (!result.isEmpty()){
                try {
                    val objJson = JsonParser().parse(result)
                    if (objJson.isJsonObject){
                        getUser(objJson.asJsonObject)

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

    private fun getUser(objJson: JsonObject){
        for (entry in objJson.entrySet()) {
            if (entry.key == "id"){
                user.id = (entry.value as JsonElement).asString?:""
            }
            if (entry.key == "name"){
                user.name = (entry.value as JsonElement).asString?:""
            }
            if (entry.key == "token"){
                user.token = (entry.value as JsonElement).asString?:""
            }

        }

        observableEndTask.onNext(user)

    }

}