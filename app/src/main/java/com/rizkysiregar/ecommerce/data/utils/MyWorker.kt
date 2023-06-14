package com.rizkysiregar.ecommerce.data.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParam: WorkerParameters) : Worker(appContext, workerParam) {
    override fun doWork(): Result {
        Log.d(TAG, "Performing running task in schedule long")
        return Result.success()
    }

    companion object {
        private val TAG = "MyWorker"
    }

}