package com.websarva.wings.android.workmanagerjava;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.websarva.wings.android.workmanagerjava.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding _activityMainBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		_activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
		View contentView = _activityMainBinding.getRoot();
		setContentView(contentView);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		_activityMainBinding.btStartChaining.setOnClickListener(new StartChainingClickListener());
		_activityMainBinding.btStartChainingSeveral.setOnClickListener(new StartChainingSeveralClickListener());
		_activityMainBinding.btStartExpedited.setOnClickListener(new StartExpeditedClickListener());
		_activityMainBinding.btStartPeriodic.setOnClickListener(new StartPeriodicClickListener());
		_activityMainBinding.btWorkCancel.setOnClickListener(new CancelWorkButtonClickListener());
	}

	private class StartChainingClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(CreateCountWorker.class);
			OneTimeWorkRequest createCountWorkRequest = workRequestBuilder.build();
			workRequestBuilder = new OneTimeWorkRequest.Builder(ReceiveCountWorker.class);
			OneTimeWorkRequest receiveCountWorkRequest = workRequestBuilder.build();
			workRequestBuilder = new OneTimeWorkRequest.Builder(FinishCountWorker.class);
			OneTimeWorkRequest finishCountWorkRequest = workRequestBuilder.build();
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			WorkContinuation workContinuation = workManager.beginWith(createCountWorkRequest);
			workContinuation = workContinuation.then(receiveCountWorkRequest);
			workContinuation = workContinuation.then(finishCountWorkRequest);
			workContinuation.enqueue();
//			workManager.beginWith(createCountWorkRequest).then(receiveCountWorkRequest).then(finishCountWorkRequest).enqueue();
		}
	}

	private class StartChainingSeveralClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(CountWithIdWorker.class);
			workRequestBuilder.setInputMerger(ArrayCreatingInputMerger.class);
			OneTimeWorkRequest countWithIdWork1Request = workRequestBuilder.build();
			OneTimeWorkRequest countWithIdWork2Request = workRequestBuilder.build();
			OneTimeWorkRequest countWithIdWork3Request = workRequestBuilder.build();
			workRequestBuilder = new OneTimeWorkRequest.Builder(FinishCountWithIdWorker.class);
			workRequestBuilder.setInputMerger(ArrayCreatingInputMerger.class);
			OneTimeWorkRequest finishCountWithIdWorkRequest = workRequestBuilder.build();
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			List<OneTimeWorkRequest> workRequestList = Arrays.asList(countWithIdWork1Request, countWithIdWork2Request, countWithIdWork3Request);
			workManager.beginWith(workRequestList).then(finishCountWithIdWorkRequest).enqueue();
		}
	}

	private class StartExpeditedClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(CountWorker.class);
			workRequestBuilder.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST);
			WorkRequest workRequest = workRequestBuilder.build();
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			workManager.enqueue(workRequest);
		}
	}

	private class StartPeriodicClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
//			PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(CountWorker.class, 20, TimeUnit.MINUTES);
			PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(CountWorker.class, 20, TimeUnit.MINUTES, 6, TimeUnit.MINUTES);
			WorkRequest workRequest = workRequestBuilder.build();
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			workManager.enqueue(workRequest);
		}
	}

	private class CancelWorkButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			workManager.cancelAllWork();
		}
	}
}
