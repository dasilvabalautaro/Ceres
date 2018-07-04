package com.globalhiddenodds.ceres.models.interfaces

import io.reactivex.Scheduler

interface IPostExecutionThread {
    val scheduler: Scheduler
}