package com.kursatmemis.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DatabaseUpdateWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val data = inputData.getInt("intKey", 0) // Gönderilen data'yı aldık.
        updateValue(data)
        return Result.success()
    }

    private fun updateValue(newValue: Int) {
        val sharedPref = context.getSharedPreferences("MyValue", Context.MODE_PRIVATE)
        val oldValue = sharedPref.getInt("value", 0)
        val savedNumber = oldValue + newValue
        val editor = sharedPref.edit()
        editor.putInt("value", savedNumber)
        Log.w("mKm - krst", savedNumber.toString())
        editor.apply()
    }

}