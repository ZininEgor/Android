package com.example.timer.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.database.Cycle
import com.example.timer.databinding.ListItemCycleBinding

class TimerAdapter : ListAdapter<Cycle, TimerAdapter.ViewHolder>(CycleDiffCallback())
{
    override fun onBindViewHolder(holder: TimerAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemCycleBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cycle) {
            binding.sleep = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCycleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class CycleDiffCallback : DiffUtil.ItemCallback<Cycle>() {
    override fun areItemsTheSame(oldItem: Cycle, newItem: Cycle): Boolean {
        return oldItem.cycleId == newItem.cycleId
    }

    override fun areContentsTheSame(oldItem: Cycle, newItem: Cycle): Boolean {
        return oldItem == newItem
    }
}