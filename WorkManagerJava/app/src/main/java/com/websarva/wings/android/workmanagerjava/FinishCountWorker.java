package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class FinishCountWorker extends Worker {
	public FinishCountWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Data inputData = getInputData();
		String loopMsg = inputData.getString("loopMsg");
		int loopCount = inputData.getInt("loopCount", -1);
		long failureCount = inputData.getLong("failureCount", -1);
		long retryCount = inputData.getLong("retryCount", -1);
		StringBuffer sb = new StringBuffer();
		sb.append("ReceiveCountWorkerが終了しました。");
		sb.append("\nループメッセージ: ");
		sb.append(loopMsg);
		sb.append("\nループ回数: ");
		sb.append(loopCount);
		sb.append("\n失敗回数: ");
		sb.append(failureCount);
		sb.append("\nリトライ回数: ");
		sb.append(retryCount);
		Log.i("FinishCountWorker", sb.toString());
		return Result.success();
	}
}
