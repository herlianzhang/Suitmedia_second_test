package com.herlianzhang.suitmedia_first_test.ui.guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herlianzhang.suitmedia_first_test.databinding.ItemGuestBinding
import com.herlianzhang.suitmedia_first_test.vo.Guest

class GuestAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Guest, GuestAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    class ViewHolder private constructor(private val binding: ItemGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Guest) {
            binding.tvMain.text = item.name
            Glide.with(binding.root)
                .load("https://med.gov.bz/wp-content/uploads/2020/08/dummy-profile-pic.jpg")
                .into(binding.ivMain)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGuestBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Guest>() {
        override fun areItemsTheSame(oldItem: Guest, newItem: Guest) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Guest, newItem: Guest) = oldItem == newItem
    }

    interface OnClickListener {
        fun onClick(guest: Guest)
    }
}