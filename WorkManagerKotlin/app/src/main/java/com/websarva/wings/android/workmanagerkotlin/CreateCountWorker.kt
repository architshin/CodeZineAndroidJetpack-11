package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class CreateCountWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		val loopCount = (1..30).random()
		val dataBuilder = Data.Builder()
		dataBuilder.putString("loopMsg", "こんにちは")
		dataBuilder.putInt("loopCount", loopCount)
		val outputData = dataBuilder.build()
		Log.i("CreateCountWorker","ループ回数として${loopCount}を生成しました。")
		return Result.success(outputData)
	}
}
