package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CountWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		Log.i("CountWorker", "20回ループを行います。")
		for(i in 1..20) {
			Log.i("CountWorker", "${i}回目")
			Thread.sleep(1000)
		}
		return Result.success()
	}
}
