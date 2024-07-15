package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.UUID;

public class CountWithIdWorker extends Worker {
	public CountWithIdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		UUID uuid = getId();
		Log.i("CountWithIdWorker", "IDが" + uuid + "で5回ループを行います。");
		Data.Builder dataBuilder = new Data.Builder();
		dataBuilder.putString("id", uuid.toString());
		Data outputData = dataBuilder.build();
		Result returnVal = Result.success(outputData);
		for(int i = 1; i <= 5; i++) {
			Log.i("CountWithIdWorker", "IDが" + uuid + "の" +  i + "回目");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				returnVal = Result.failure();
			}
		}
		return returnVal;
	}
}
