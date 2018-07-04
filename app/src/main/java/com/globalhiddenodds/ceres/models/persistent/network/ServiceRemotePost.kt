package com.globalhiddenodds.ceres.models.persistent.network

import com.globalhiddenodds.ceres.models.interfaces.IServicePost

class ServiceRemotePost: BaseServiceRemote() {
    private val tag = ServiceRemotePost::class.java.name!!
    private val error = "$tag: IllegalArgument"

    fun getService(): IServicePost {
        return getClient().
                create(IServicePost::class.java)?:
        throw IllegalArgumentException(error)
    }
}