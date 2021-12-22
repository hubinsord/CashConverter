package com.thedroidsonroids.cashconverter.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thedroidsonroids.cashconverter.data.model.Rate
import com.thedroidsonroids.cashconverter.databinding.ListingItemBinding

class ListingAdapter : ListAdapter<Rate, ListingAdapter.ViewHolder>(CurrencyComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(rate: Rate){
            binding.apply {
                tvCurrency.text = rate.code
                tvBid.text = rate.bid.toString()
                tvAsk.text = rate.ask.toString()
            }
        }
    }

    class CurrencyComparator: DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean =
            oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean =
            oldItem == newItem
    }
}