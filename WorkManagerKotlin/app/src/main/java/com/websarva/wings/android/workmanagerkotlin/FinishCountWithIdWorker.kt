package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FinishCountWithIdWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		val idList = inputData.getStringArray("id")
		val sb = StringBuffer()
		if (idList != null) {
			for(id in idList) {
				sb.append("\n${id}")
			}
		}
		Log.i("FinishCountWithIdWorker","CountWithIdWorkerが終了しました。\n終了したid: ${sb}")
		return Result.success()
	}
}
