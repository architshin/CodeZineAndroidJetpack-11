package com.websarva.wings.android.workmanagerkotlin

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.ArrayCreatingInputMerger
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.websarva.wings.android.workmanagerkotlin.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
	private lateinit var _activityMainBinding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		_activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(_activityMainBinding.root)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		_activityMainBinding.btStartChaining.setOnClickListener(StartChainingClickListener())
		_activityMainBinding.btStartChainingSeveral.setOnClickListener(StartChainingSeveralClickListener())
		_activityMainBinding.btStartExpedited.setOnClickListener(StartExpeditedClickListener())
		_activityMainBinding.btStartPeriodic.setOnClickListener(StartPeriodicClickListener())
		_activityMainBinding.btWorkCancel.setOnClickListener(CancelWorkButtonClickListener())
	}

	private inner class StartChainingClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			var workRequestBuilder = OneTimeWorkRequestBuilder<CreateCountWorker>()
			val createCountWorkRequest = workRequestBuilder.build()
			workRequestBuilder = OneTimeWorkRequestBuilder<ReceiveCountWorker>()
			val receiveCountWorkRequest = workRequestBuilder.build()
			workRequestBuilder = OneTimeWorkRequestBuilder<FinishCountWorker>()
			val finishCountWorkRequest = workRequestBuilder.build()
			val workManager = WorkManager.getInstance(this@MainActivity)
			var workContinuation = workManager.beginWith(createCountWorkRequest)
			workContinuation = workContinuation.then(receiveCountWorkRequest)
			workContinuation = workContinuation.then(finishCountWorkRequest)
			workContinuation.enqueue()
//			workManager.beginWith(createCountWorkRequest).then(receiveCountWorkRequest).then(finishCountWorkRequest).enqueue()
		}
	}

	private inner class StartChainingSeveralClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			var workRequestBuilder = OneTimeWorkRequestBuilder<CountWithIdWorker>()
			workRequestBuilder.setInputMerger(ArrayCreatingInputMerger::class.java)
			val countWithIdWork1Request = workRequestBuilder.build()
			val countWithIdWork2Request = workRequestBuilder.build()
			val countWithIdWork3Request = workRequestBuilder.build()
			workRequestBuilder = OneTimeWorkRequestBuilder<FinishCountWithIdWorker>()
			workRequestBuilder.setInputMerger(ArrayCreatingInputMerger::class.java)
			val finishCountWithIdWorkRequest = workRequestBuilder.build()
			val workManager = WorkManager.getInstance(this@MainActivity)
			val workRequestList = listOf(countWithIdWork1Request, countWithIdWork2Request, countWithIdWork3Request)
			workManager.beginWith(workRequestList).then(finishCountWithIdWorkRequest).enqueue()
		}
	}

	private inner class StartExpeditedClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			var workRequestBuilder = OneTimeWorkRequestBuilder<CountWorker>()
			workRequestBuilder.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
			val workRequest = workRequestBuilder.build()
			val workManager = WorkManager.getInstance(this@MainActivity)
			workManager.enqueue(workRequest)
		}
	}

	private inner class StartPeriodicClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
//			val workRequestBuilder = PeriodicWorkRequestBuilder<CountWorker>(20, TimeUnit.MINUTES)
			val workRequestBuilder = PeriodicWorkRequestBuilder<CountWorker>(20, TimeUnit.MINUTES, 6, TimeUnit.MINUTES)
			val workRequest = workRequestBuilder.build()
			val workManager = WorkManager.getInstance(this@MainActivity)
			workManager.enqueue(workRequest)
		}
	}

	private inner class CancelWorkButtonClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			val workManager = WorkManager.getInstance(this@MainActivity)
			workManager.cancelAllWork()
		}
	}
}
