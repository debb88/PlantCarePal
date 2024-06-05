package com.example.plant.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.ListHistory
import com.example.plant.databinding.ItemRowHistoryBinding
import com.example.plant.ui.detail.DetailActivity

class HistoryAdapter: ListAdapter<ListHistory, HistoryAdapter.ListViewHolder>(DIFF_CALLBACK) {


    class ListViewHolder(val binding: ItemRowHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history : ListHistory) {
            binding.imgCardLeaf.setImageResource(history.photo)
            binding.namaPenyakit.text = "${history.name}"
            binding.percentage.text = "${history.percentage}"
            binding.time.text = "${history.time}"

            itemView.setOnClickListener {
                val intentDetail =Intent(itemView.context, DetailActivity::class.java)

                intentDetail.putExtra(DetailActivity.PHOTO_DETAIL, history.photo)
                intentDetail.putExtra(DetailActivity.DISEASE_NAME, history.name)
                intentDetail.putExtra(DetailActivity.PERCENTAGE, history.percentage)
                itemView.context.startActivity(intentDetail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history =getItem(position)
        holder.bind(history)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListHistory>() {
            override fun areItemsTheSame(
                oldItem: ListHistory,
                newItem: ListHistory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListHistory,
                newItem: ListHistory
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}