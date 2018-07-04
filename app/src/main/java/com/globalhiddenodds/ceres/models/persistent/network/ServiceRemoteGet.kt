package com.globalhiddenodds.ceres.models.persistent.network

import com.globalhiddenodds.ceres.models.interfaces.IServiceGet

class ServiceRemoteGet: BaseServiceRemote() {
    private val tag = ServiceRemoteGet::class.java.name!!
    private val error = "$tag: IllegalArgument"

    fun getService(): IServiceGet {
        return getClient().
                create(IServiceGet::class.java)?:
        throw IllegalArgumentException(error)
    }
}