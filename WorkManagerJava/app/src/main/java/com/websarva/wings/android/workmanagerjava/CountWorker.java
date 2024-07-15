package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CountWorker extends Worker {
	public CountWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Log.i("CountWorker", "20回ループを行います。");
		Result returnVal = Result.success();
		for(int i = 1; i <= 20; i++) {
			Log.i("CountWorker", i + "回目");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				returnVal = Result.failure();
			}
		}
		return returnVal;
	}
}
