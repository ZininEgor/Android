package com.example.timer.timer

import android.app.*
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import android.os.CountDownTimer
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import android.media.MediaPlayer
import com.example.timer.MainActivity
import com.example.timer.R
import com.example.timer.database.Cycle
import com.example.timer.database.SleepDatabase
import com.example.timer.others.Constants.ACTION_SHOW_TIMER_FRAGMENT
import com.example.timer.others.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.timer.others.Constants.ACTION_STOP_SERVICE
import com.example.timer.others.Constants.NOTIFICATION_CHANNEL_ID
import com.example.timer.others.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.timer.others.Constants.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.example.timer.database.SleepDatabaseDao
import com.example.timer.others.Constants.ACTION_NEXT_BUTTON
import com.example.timer.others.Constants.ACTION_PAUSE_BUTTON
import com.example.timer.others.Constants.ACTION_PREVIEW_BUTTON
//import com.example.timer.timer.TimerViewModel
import kotlinx.coroutines.launch

class MyService : LifecycleService() {


    var mMediaPlayer: MediaPlayer? = null
    var isFirstRun = true
    var serviceKilled = false
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var timer: CountDownTimer


    companion object {
        const val DONE = 0L

        const val ONE_SECOND = 1000L

        var COUNTDOWN_TIME = 10000L

        var pause = MutableLiveData<Boolean>()
        var cycles = MutableLiveData<List<Cycle>>()
        var currentStep = MutableLiveData<Int>()
        var key = MutableLiveData<Long>()
        var nameStep = MutableLiveData<String>()
        var backgroundColor = MutableLiveData<String>()
        lateinit var database: SleepDatabaseDao
        private val _currentTime = MutableLiveData<Long>()
        val currentTime: LiveData<Long>
            get() = _currentTime

    }

    override fun onCreate() {
        super.onCreate()

        database = SleepDatabase.getInstance(application).sleepDatabaseDao
        scope.launch {
            cycles.value = key.value?.let { it1 -> database.cycles(key = key.value!!) }!!
        }

        key.observe(this, Observer {

            scope.launch {
                cycles.value = key.value?.let { it1 -> database.cycles(key = key.value!!) }!!
            }

        })
        _currentTime.observe(this, Observer { second ->
            if (second in 1..3) {
                playSound()
            }
            if (second in 0..0) {
                playSoundEnd()
            }
        })
    }

    init {
        currentStep.value = -1
        timer = object : CountDownTimer(0L, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        key.value = intent?.getLongExtra("id", 0L)!!
        intent.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        Log.i("MyService", key.toString())
                        isFirstRun = false
                        onNextStep()
                    } else {
                        Log.i("MyService", "Resuming Service...")
                    }
                }
                ACTION_PREVIEW_BUTTON -> {
                    onPreviewStep()
                }
                ACTION_NEXT_BUTTON -> {
                    onNextStep()
                }
                ACTION_PAUSE_BUTTON -> {
                    onStopResume()
                }
                ACTION_STOP_SERVICE -> {
                    killService()
                    Log.i("MyService", "Stopped service")
                }
                else -> Log.i("MyService", "Another action")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun playSound() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.pickup)
        mMediaPlayer!!.isLooping = false
        mMediaPlayer!!.start()
    }

    private fun playSoundEnd() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.next)
        mMediaPlayer!!.isLooping = false
        mMediaPlayer!!.start()

    }

    fun onNextStep() {

        timer.cancel()
        if (cycles.value?.size == currentStep.value?.plus(1)) {
            _currentTime.value = DONE
            killService()
            return
        }
        if (currentStep.value != -1) {
            scope.launch {
                currentStep.value?.let { cycles.value?.get(it) }?.let { makeUsualStep(it) }
            }
        }
        currentStep.value = currentStep.value?.plus(1)
        val step = currentStep.value?.let { cycles.value?.get(it) }
        if (step != null) {
            scope.launch {
                makeCurrentStep(step)
            }
            nameStep.value = resources.getString(step.name)
            COUNTDOWN_TIME = step.time.toLong() * 1000
            backgroundColor.value = step.stringColor
        }
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                onNextStep()
            }
        }
        timer.start()
    }


    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        createNotificationChannel(notificationManager)

        val notificationBuilded = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.icons8_boy_avatar)
            .setContentTitle("Runing app")
            .setColorized(true)
            .setContentText(Observer<MutableLiveData<Int>> {
                _currentTime.value

            }.toString())
            .setContentIntent(getMainActivityPendingIntent())

        _currentTime.observe(this, Observer {
            notificationBuilded.setContentText(_currentTime.value.toString())
            startForeground(NOTIFICATION_ID, notificationBuilded.build())
        })
        nameStep.observe(this, Observer {
            notificationBuilded.setContentTitle(nameStep.value.toString())
            startForeground(NOTIFICATION_ID, notificationBuilded.build())
        })
        backgroundColor.observe(this, Observer {
            notificationBuilded.setColor(Color.parseColor(backgroundColor.value))
            startForeground(NOTIFICATION_ID, notificationBuilded.build())
        })
        startForeground(NOTIFICATION_ID, notificationBuilded.build())

    }

    private fun onPreviewStep() {


        timer.cancel()
        if (currentStep.value!! == 0) {
            _currentTime.value = DONE
            return
        }
        scope.launch {
            currentStep.value?.let { cycles.value?.get(it) }?.let { makeUsualStep(it) }
        }
        currentStep.value = currentStep.value?.minus(1)
        val step = currentStep.value?.let { cycles.value?.get(it) }
        backgroundColor.value = step?.stringColor
        if (step != null) {
            scope.launch {
                makeCurrentStep(step)
            }
            nameStep.value = resources.getString(step.name)
            COUNTDOWN_TIME = step.time.toLong() * 1000
        }
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                onNextStep()
            }
        }
        timer.start()
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TIMER_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )

    private fun onStopResume() {
        if (pause.value == true) {
            timer.start()
            pause.value = false
        } else {
            timer.cancel()
            pause.value = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun makeCurrentStep(cycle: Cycle) {
        cycle.current = true
        database.updateCycle(cycle)
    }

    private suspend fun makeUsualStep(cycle: Cycle) {
        cycle.current = false
        database.updateCycle(cycle)
    }

    private fun killService() {
        serviceKilled = true
        isFirstRun = true
        stopForeground(true)
        stopSelf()
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)

    }
}