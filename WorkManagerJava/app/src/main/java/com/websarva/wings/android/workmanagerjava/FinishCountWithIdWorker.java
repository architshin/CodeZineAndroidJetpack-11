package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class FinishCountWithIdWorker extends Worker {
	public FinishCountWithIdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Data inputData = getInputData();
		String[] idList = inputData.getStringArray("id");
		StringBuffer sb = new StringBuffer();
		sb.append("CountWithIdWorkerが終了しました。");
		sb.append("\n終了したid: ");
		for(String id: idList) {
			sb.append("\n");
			sb.append(id);
		}
		Log.i("FinishCountWithIdWorker", sb.toString());
		return Result.success();
	}
}
