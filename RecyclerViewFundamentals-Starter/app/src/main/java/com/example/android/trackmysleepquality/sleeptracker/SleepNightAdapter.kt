package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding):RecyclerView.ViewHolder(binding.root){

        public fun bind(item: SleepNight) {
            binding.sleep=item
            binding.executePendingBindings()
        }

        companion object {
            public fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)//parent指RecyclerView
                val binding = ListItemSleepNightBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        //確認在資料庫裏面是不是同筆資料
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        //確認內容是否相通
        return oldItem == newItem
    }
}