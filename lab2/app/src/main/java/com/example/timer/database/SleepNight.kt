package com.example.timer.database

import androidx.room.*


@Entity(tableName = "title_table")
data class TitleWorkout(

        @PrimaryKey(autoGenerate = true)
        var titleWorkoutId: Long = 0L,

        @ColumnInfo(name = "name")
        var name: String = "Тренировка",

        @ColumnInfo(name = "ready")
        var ready: String = "10",

        @ColumnInfo(name = "work")
        var work: String = "20",

        @ColumnInfo(name = "set")
        var set: String = "1",

        @ColumnInfo(name = "ch_between_set")
        var chill_between_set: String = "1",

        @ColumnInfo(name = "chill")
        var chill: String = "10",

        @ColumnInfo(name = "count_cycles")
        var count: String = "8",

        @ColumnInfo(name = "color")
        var color: String = "#388310",

        @ColumnInfo(name = "cycle")
        var cycle: String = "8"


)

@Entity(tableName = "cycle_table")
data class Cycle(

        @PrimaryKey(autoGenerate = true)
        var cycleId: Long = 0L,
        var titleWorkoutCreatorId: Long,
        var name: Int,
        var stringColor: String,
        var idForTimer: Int,
        var time: Int,
        var current: Boolean = false


)

data class TitleWorkoutWithCycles(
        @Embedded val title: TitleWorkout,
        @Relation(
                parentColumn = "titleWorkoutId",
                entityColumn = "titleWorkoutCreatorId"
        )
        val playlists: List<Cycle>
)
