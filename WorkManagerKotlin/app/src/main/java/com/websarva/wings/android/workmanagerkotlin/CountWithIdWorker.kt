package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class CountWithIdWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		Log.i("CountWorker", "IDが${id}で5回ループを行います。")
		val dataBuilder = Data.Builder()
		dataBuilder.putString("id", id.toString())
		val outputData = dataBuilder.build()
		for(i in 1..5) {
			Log.i("CountWorker", "IDが${id}の${i}回目")
			Thread.sleep(1000)
		}
		return Result.success(outputData)
	}
}
