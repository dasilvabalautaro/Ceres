package com.globalhiddenodds.ceres.models.persistent.network

data class MessageOfService(val success: Boolean,
                            val result: Any,
                            val error: Any) {
}