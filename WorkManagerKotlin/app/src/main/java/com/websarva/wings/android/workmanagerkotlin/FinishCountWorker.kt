package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FinishCountWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		val loopMsg = inputData.getString("loopMsg")
		val loopCount = inputData.getInt("loopCount", 30)
		val failureCount = inputData.getLong("failureCount", -1)
		val retryCount = inputData.getLong("retryCount", -1)
		Log.i("FinishCountWorker","ReceiveCountWorkerが終了しました。\nループメッセージ: ${loopMsg}\nループ回数: ${loopCount}\n失敗回数: ${failureCount}\nリトライ回数: ${retryCount}")
		return Result.success()
	}
}
