package com.globalhiddenodds.ceres.models.executor

import com.globalhiddenodds.ceres.models.interfaces.IThreadExecutor
import java.util.concurrent.*
import javax.inject.Inject


class JobExecutor @Inject constructor() : IThreadExecutor {
    private var workQueue: BlockingQueue<Runnable>? = null

    private var threadPoolExecutor: ThreadPoolExecutor? = null

    private var threadFactory: ThreadFactory? = null

    init {
        this.workQueue = LinkedBlockingQueue<Runnable>()
        this.threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE,
                MAX_POOL_SIZE, KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT,
                this.workQueue, this.threadFactory)
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }
        this.threadPoolExecutor!!.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }

        companion object {
            private const val THREAD_NAME = "android_"
        }
    }

    companion object {
        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5
        private const val KEEP_ALIVE_TIME = 10
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

}