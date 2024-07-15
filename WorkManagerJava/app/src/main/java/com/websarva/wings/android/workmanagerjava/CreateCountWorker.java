package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CreateCountWorker extends Worker {
	public CreateCountWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		int loopCount = (int) Math.round(Math.random() * 30);
		Data.Builder dataBuilder = new Data.Builder();
		dataBuilder.putString("loopMsg", "こんにちは");
		dataBuilder.putInt("loopCount", loopCount);
		Data outputData = dataBuilder.build();
		Log.i("CreateCountWorker", "ループ回数として" + loopCount + "を生成しました。");
		return Result.success(outputData);
	}
}
