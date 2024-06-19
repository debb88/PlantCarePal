package com.example.plant.ui.history

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.ListHistory
import com.example.plant.databinding.ItemRowHistoryBinding
import com.example.plant.ui.detail.DetailActivity
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import com.example.plant.ui.network.response.Data
import com.example.plant.ui.network.response.DataItem

class HistoryAdapter(private val viewModelStoreOwner: ViewModelStoreOwner, private val showDeleteButton: Boolean): ListAdapter<DataItem, HistoryAdapter.ListViewHolder>(DIFF_CALLBACK) {


    class ListViewHolder(val binding: ItemRowHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history : DataItem, viewModelStoreOwner: ViewModelStoreOwner, showDeleteButton: Boolean) {
            Glide.with(itemView.context)
                .load("${history.imageUrl}")
                .into(binding.imgCardLeaf)
            val percentage = "${history.percentage}"
            val percentageS = percentage.split(".")
            val percentageA = percentageS[1].substring(0,2)
            val percentageF = "(${percentageS[0]}.$percentageA%)"

            if(showDeleteButton){
                binding.fabDelete.visibility = View.VISIBLE
            }else{
                binding.fabDelete.visibility = View.GONE
            }

            val time = "${history.createdAt}"
            val timeS = time.split("T")
            val timeF = timeS[0]
            binding.namaPenyakit.text = "${history.diseasesName}"
            binding.percentage.text = "${percentageF}"
            binding.time.text = timeF



            itemView.setOnClickListener {
                val intentDetail =Intent(itemView.context, DetailActivity::class.java)
                val optionsCompat: ActivityOptionsCompat=
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgCardLeaf,"img_plant"),
                        Pair(binding.namaPenyakit, "name"),
                        Pair(binding.percentage, "percentage")

                    )
                intentDetail.putExtra(DetailActivity.ID, "${history.id}")
                itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
            }

            binding.fabDelete.setOnClickListener {
                val deleteViewModel  = ViewModelProvider(viewModelStoreOwner).get(DeleteViewModel::class.java)
                deleteViewModel.setIsClickId(true)
                deleteViewModel.setIdDelete("${history.id}")
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history =getItem(position)
        holder.bind(history, viewModelStoreOwner, showDeleteButton)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
        const val TAG = "HistoryAdapter"
    }
}