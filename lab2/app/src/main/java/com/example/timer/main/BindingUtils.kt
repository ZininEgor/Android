/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.timer.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.example.timer.R
import com.example.timer.database.Cycle
import com.example.timer.database.TitleWorkout


@BindingAdapter("id")
fun TextView.setSleepDurationFormatted(item: TitleWorkout?) {
    item?.let {
        text = item.titleWorkoutId.toString()
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("timer")
fun TextView.timer(item: Cycle?)
{
    item?.let {
        text = item.idForTimer.toString()+". "+resources.getString(item.name)+": "+item.time.toString()
    }
}

@BindingAdapter("title")
fun TextView.Title(item: TitleWorkout?) {
    item?.let {
        text = item.name.toString()
    }
}



@SuppressLint("ResourceType")
@BindingAdapter("ready")
fun TextView.Ready(item: TitleWorkout?) {
    val s = resources.getString(R.string.Ready)
    val sec = resources.getString(R.string.Sec)
    item?.let {
        text = s + " : " + item.ready + " " + sec
    }
}

@BindingAdapter("ready_raw")
fun TextView.ReadyRaw(item: TitleWorkout?) {
    item?.let {
        text = item.ready.toString()
    }
}

@BindingAdapter("work")
fun TextView.work(item: TitleWorkout?) {
    val s = resources.getString(R.string.Work)
    val sec = resources.getString(R.string.Sec)
    item?.let {
        text = s + " : " + item.work + " " + sec
    }
}

@BindingAdapter("work_raw")
fun TextView.workRaw(item: TitleWorkout?) {
    item?.let {
        text = item.work.toString()
    }
}

@BindingAdapter("chill")
fun TextView.chill(item: TitleWorkout?) {
    val s = resources.getString(R.string.Chill)
    val sec = resources.getString(R.string.Sec)
    item?.let {
        text = s + " : " + item.chill + " " + sec
    }
}

@BindingAdapter("chill_raw")
fun TextView.chill_raw(item: TitleWorkout?) {
    item?.let {
        text = item.chill.toString()
    }
}

@BindingAdapter("Cycle")
fun TextView.Cycle(item: TitleWorkout?) {
    val s = resources.getString(R.string.Cycles)
    val times = resources.getString(R.string.Times)
    item?.let {
        text = s + " : " + item.cycle + " " + times
    }
}

@BindingAdapter("Cycle_raw")
fun TextView.CycleRaw(item: TitleWorkout?) {
    item?.let {
        text = item.cycle
    }
}

@BindingAdapter("colortitle")
fun ImageButton.colorfor(item: TitleWorkout)
{
    item?.let {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.done)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color))

        if (item.color == "#cb75fd")
        {
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable

        }
        if (item.color == "#ffcb00")
        {
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable

        }
        if (item.color == "#b01717")
        {
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable

        }
        if (item.color == "#0b09ae")
        {
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable
        }
        if (item.color == "#198c19")
        {
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable
        }
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("color")
fun ConstraintLayout.color(item: TitleWorkout?)
{

    item?.let {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.viewholdershape)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color))
        background = wrappedDrawable
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("backgroundColor")
fun ConstraintLayout.backgroundColor(item: Cycle?)
{
    item?.let {
        if (item.current)
        {
            setBackgroundColor(Color.parseColor("#99000000"))
        }
        else
        {
            background = null
        }
    }
}


@BindingAdapter("set")
fun TextView.set(item: TitleWorkout?) {
    item?.let {
        text =  item.set.toString()
    }
}

@BindingAdapter("chill_set")
fun TextView.chill_set(item: TitleWorkout?) {
    item?.let {
        text =  item.chill_between_set.toString()
    }
}

@BindingAdapter("all")
fun TextView.All(item: TitleWorkout?) {
    val s = resources.getString(R.string.Total)
    item?.let {
        text = s + " : "+(((item.work.toInt()+item.chill.toInt())*item.cycle.toInt())/60).toString() + " · " + (item.cycle.toInt() * 2).toString() + ""
    }
}



