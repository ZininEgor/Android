package com.example.timer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {

    @Transaction
    @Query("SELECT * FROM title_table")
    fun getUsersWithPlaylists(): List<TitleWorkoutWithCycles>

    @Insert
    suspend fun insertTitle(title: TitleWorkout)


    @Query("SELECT * FROM cycle_table WHERE titleWorkoutCreatorId = :key")
    suspend fun cycles(key: Long): List<Cycle>


    @Update
    suspend fun updateCycle(cycle: Cycle)

    @Query("DELETE FROM cycle_table WHERE titleWorkoutCreatorId = :key")
    suspend fun deleteCycles(key: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCycle(objects: List<Cycle>)

    @Query("DELETE FROM title_table WHERE titleWorkoutId = :key ")
    suspend fun deleteTitle(key: Long)


    @Update
    suspend fun updateTitle(night: TitleWorkout)


    @Query("SELECT * from title_table WHERE titleWorkoutId = :key")
    suspend fun getTitle(key: Long): TitleWorkout?

    @Query("SELECT * FROM title_table ORDER BY titleWorkoutId DESC")
    fun getAllTitles(): LiveData<List<TitleWorkout>>

    @Query("DELETE FROM title_table")
    suspend fun clear()


    @Query("SELECT * from title_table WHERE titleWorkoutId = :key")
    fun getWorkoutWithId(key: Long): LiveData<TitleWorkout>


}


