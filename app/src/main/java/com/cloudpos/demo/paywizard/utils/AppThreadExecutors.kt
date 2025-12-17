package com.cloudpos.demo.paywizard.utils

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.Volatile

class AppThreadExecutors private constructor() {

    // 1. IO-intensive thread pool (e.g., network requests, file read/write)
    // Characteristics: Tasks take a long time, so the number of threads can be higher (usually the number of CPU cores * 2)
    private val mIoExecutor: ExecutorService

    // 2. CPU-intensive thread pools (such as data parsing, complex calculations)
    // Characteristics: Tasks consume CPU resources, and the number of threads should not be too large (usually equal to the number of CPU cores).
    private val mCpuExecutor: ExecutorService

    // 3. Lightweight Task Thread Pool (e.g., fast local data processing)
    // Features: Lightweight, high-frequency tasks, processed using core threads, avoiding frequent thread creation and destruction
    private val mLightExecutor: ExecutorService

    init {
        var cpuCount = Runtime.getRuntime().availableProcessors()
        if (cpuCount <= 0) {
            cpuCount = 1
        }
        // IO thread pool configuration
        mIoExecutor = ThreadPoolExecutor(
            cpuCount * 2,  // Core thread count
            cpuCount * 4,  // Max thread count
            60L,  // Idle thread lifespan
            TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable?>(128),  // Task queue
            ThreadPoolExecutor.AbortPolicy()
        )

        // CPU thread pool configuration
        mCpuExecutor = ThreadPoolExecutor(
            cpuCount,  // Core thread count = CPU core count
            cpuCount,  // Maximum number of threads = Core number of threads (to avoid creating extra threads)
            60L,
            TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable?>(32),
            ThreadPoolExecutor.AbortPolicy()
        )

        // Lightweight task thread pool (fixed core thread count, no maximum thread count limit, unbounded queue)
        mLightExecutor = ThreadPoolExecutor(
            2,  // Fixed 2 core threads
            Int.Companion.MAX_VALUE,
            60L,
            TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable?>(64)
        )
    }

    /**
     * IO intensive
     */
    fun io(): ExecutorService {
        return mIoExecutor
    }

    /**
     * CPU computing
     */
    fun cpu(): ExecutorService {
        return mCpuExecutor
    }

    fun light(): ExecutorService {
        return mLightExecutor
    }

    fun shutdown() {
        mIoExecutor.shutdown()
        mCpuExecutor.shutdown()
        mLightExecutor.shutdown()
    }

    companion object {
        @Volatile
        private var sInstance: AppThreadExecutors? = null

        val instance: AppThreadExecutors?
            get() {
                if (sInstance == null) {
                    synchronized(AppThreadExecutors::class.java) {
                        if (sInstance == null) {
                            sInstance = AppThreadExecutors()
                        }
                    }
                }
                return sInstance
            }
    }

}