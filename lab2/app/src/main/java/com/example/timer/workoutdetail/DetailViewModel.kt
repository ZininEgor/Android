package com.example.timer.workoutdetail

import androidx.lifecycle.*
import com.example.timer.R
import com.example.timer.database.Cycle
import com.example.timer.database.SleepDatabaseDao
import com.example.timer.database.TitleWorkout
import kotlinx.coroutines.launch


class DetailViewModel(
        val sleepNightKey: Long = 0L,
        dataSource: SleepDatabaseDao
) : ViewModel() {

    val database = dataSource
    private var night = MediatorLiveData<TitleWorkout>()
    var setString = MutableLiveData<String>()
    var readyString = MutableLiveData<String>()
    var workString = MutableLiveData<String>()
    var chillString = MutableLiveData<String>()
    var color = MutableLiveData<String>()
    var cycleString = MutableLiveData<String>()
    fun getNight() = night



    var chillWithSetString = MutableLiveData<String>()
    private var titleWorkout = TitleWorkout()

    init {
        night.addSource(database.getWorkoutWithId(sleepNightKey), night::setValue)
        viewModelScope.launch {
            getTitle()
        }
    }


    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    private val _navigateToTimer = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    val navigateToTimer: LiveData<Boolean?>
        get() = _navigateToTimer

    fun doneNavigatingToTimer() {
        _navigateToTimer.value = null
    }

    fun doneNavigating(title: String) {
        viewModelScope.launch {
            clearCycles()
        }
        var cycles: MutableList<Cycle> = mutableListOf()
        cycles.add(Cycle(idForTimer = 0, name = R.string.Ready, time = readyString.value?.toInt()!!, titleWorkoutCreatorId = sleepNightKey, stringColor="#4c4cff"))
        for (i in 1..cycleString.value?.toInt()!!+1) {
            if (i % 2 != 0) {
                cycles.add(Cycle( idForTimer = i , name = R.string.Work, time = workString.value?.toInt()!!, titleWorkoutCreatorId = sleepNightKey, stringColor = "#ff4c4c"))
            } else {
                cycles.add(Cycle(idForTimer = i,name = R.string.Chill, time = chillString.value?.toInt()!!, titleWorkoutCreatorId = sleepNightKey, stringColor = "#a64ca6"))
            }
        }
        cycles.add(Cycle(idForTimer = cycleString.value?.toInt()!!+2,name = R.string.cold, time = chillWithSetString.value?.toInt()!!, titleWorkoutCreatorId = sleepNightKey, stringColor = "#0080FF"))
        cycles.add(Cycle(idForTimer = cycleString.value?.toInt()!!+3, name = R.string.finish, time = 0, titleWorkoutCreatorId = sleepNightKey, stringColor = "#a64ca6"))
        viewModelScope.launch {

            addCycle(cycles)
        }
        _navigateToSleepTracker.value = null

        viewModelScope.launch {

            close(title)
        }
    }

    fun plus_ready() {
        readyString.value = (readyString.value?.toInt()?.plus(1)).toString()
    }

    fun minus_ready() {
        readyString.value = (readyString.value?.toInt()?.minus(1)).toString()
    }

    fun plus_work() {
        workString.value = (workString.value?.toInt()?.plus(1)).toString()
    }

    fun minus_work() {
        workString.value = (workString.value?.toInt()?.minus(1)).toString()
    }

    fun plus_chill() {
        chillString.value = (chillString.value?.toInt()?.plus(1)).toString()
    }

    fun minus_chill() {
        chillString.value = (chillString.value?.toInt()?.minus(1)).toString()
    }

    fun plus_cycle() {
        cycleString.value = (cycleString.value?.toInt()?.plus(1)).toString()
    }

    fun minus_cycle() {
        cycleString.value = (cycleString.value?.toInt()?.minus(1)).toString()
    }

    fun plus_set() {
        setString.value = (setString.value?.toInt()?.plus(1)).toString()
    }

    fun minus_set() {
        setString.value = (setString.value?.toInt()?.minus(1)).toString()
    }

    fun plus_chillwithset() {
        chillWithSetString.value = (chillWithSetString.value?.toInt()?.plus(1)).toString()
    }

    fun minus_chillwithset() {
        chillWithSetString.value = (chillWithSetString.value?.toInt()?.minus(1)).toString()
    }


    fun onClose() {
        _navigateToSleepTracker.value = true
    }

    fun onTimer() {
        _navigateToTimer.value = true
    }


    fun onDeleteTitle() {

        viewModelScope.launch {
            clearCycles()
        }
        viewModelScope.launch {
            delete()
        }
        _navigateToSleepTracker.value = true
    }

    suspend fun delete() {
        database.deleteTitle(sleepNightKey)
    }

    suspend fun getTitle() {
        titleWorkout = database.getTitle(sleepNightKey)!!
        readyString.value = titleWorkout.ready
        workString.value = titleWorkout.work
        color.value = titleWorkout.color
        chillString.value = titleWorkout.chill
        cycleString.value = titleWorkout.cycle
        setString.value = titleWorkout.set
        chillWithSetString.value = titleWorkout.chill_between_set
    }

    fun save(title: String)
    {
        viewModelScope.launch {
            close(title)
        }
    }

    fun color_button1()
    {
        color.value = "#cb75fd"
    }
    fun color_button2()
    {
        color.value = "#ffcb00"
    }
    fun color_button3()
    {
        color.value = "#b01717"
    }
    fun color_button4()
    {
        color.value = "#0b09ae"
    }
    fun color_button5()
    {
        color.value = "#198c19"
    }

    suspend fun close(title: String) {
        var TitleWorkoutNew = TitleWorkout()
        TitleWorkoutNew.titleWorkoutId = sleepNightKey
        TitleWorkoutNew.name = title
        TitleWorkoutNew.ready = readyString.value.toString()
        TitleWorkoutNew.work = workString.value.toString()
        TitleWorkoutNew.chill = chillString.value.toString()
        TitleWorkoutNew.cycle = cycleString.value.toString()
        TitleWorkoutNew.color = color.value.toString()
        TitleWorkoutNew.set = setString.value.toString()
        TitleWorkoutNew.chill_between_set = chillWithSetString.value.toString()
        database.updateTitle(TitleWorkoutNew)
    }

    suspend fun addCycle(objects: List<Cycle>) {
        database.insertCycle(objects)
    }

    suspend fun clearCycles() {
        database.deleteCycles(sleepNightKey)
    }

}


