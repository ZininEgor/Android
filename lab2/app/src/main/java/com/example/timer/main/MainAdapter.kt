
package com.example.timer.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.database.TitleWorkout
import com.example.timer.databinding.ListItemTrainingBinding

class TitleWorkoutAdapter(val clickListener: TitleWorkoutListener) : ListAdapter<TitleWorkout,
        TitleWorkoutAdapter.ViewHolder>(TitleWorkoutDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener,item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemTrainingBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: TitleWorkoutListener, item: TitleWorkout) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTrainingBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TitleWorkoutDiffCallback : DiffUtil.ItemCallback<TitleWorkout>() {
    override fun areItemsTheSame(oldItem: TitleWorkout, newItem: TitleWorkout): Boolean {
        return oldItem.titleWorkoutId == newItem.titleWorkoutId
    }

    override fun areContentsTheSame(oldItem: TitleWorkout, newItem: TitleWorkout): Boolean {
        return oldItem == newItem
    }
}

class TitleWorkoutListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: TitleWorkout) = clickListener(night.titleWorkoutId)
}