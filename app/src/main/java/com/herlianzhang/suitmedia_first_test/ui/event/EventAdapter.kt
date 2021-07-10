package com.herlianzhang.suitmedia_first_test.ui.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herlianzhang.suitmedia_first_test.databinding.ItemEventBinding
import com.herlianzhang.suitmedia_first_test.vo.Event

class EventAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Event, EventAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item.name.toString())
        }
    }

    class ViewHolder private constructor(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Event) {
            binding.tvName.text = item.name
            binding.tvDate.text = item.date
            Glide.with(binding.root).load(item.image).into(binding.ivMain)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEventBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Event>() {

        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }

    interface OnClickListener {
        fun onClick(name: String)
    }
}