package com.example.timer.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.database.SleepDatabaseDao
import com.example.timer.database.TitleWorkout
import kotlinx.coroutines.*
import java.util.*

/**
 * ViewModel for MainFragment.
 */
class MainViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    var listcolor = listOf("#cb75fd", "#ffcb00", "#b01717", "#0b09ae", "#198c19")

    val titles = database.getAllTitles()
    private val _navigateToSleepDataQuality = MutableLiveData<Long>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDataQuality.value = id
    }
    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
    private suspend fun create(title: TitleWorkout){
        withContext(Dispatchers.IO){
            database.insertTitle(title)
        }

    }
    fun onCreateTitle(){
        viewModelScope.launch {
            val newTitle = TitleWorkout(color = listcolor[Random().nextInt(5)])
            create(newTitle)
        }
    }
    fun onClear() {
        viewModelScope.launch {
            clear()

        }

    }

}
