package com.weatherapptask.ui.fragments.home.adapter.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.weatherapptask.databinding.ItemSearchBinding
import com.weatherapptask.network.search.model.response.ResponseSearchItem
import com.weatherapptask.ui.base.BaseViewHolder


class SearchCityAdapter(val clickOnResult: (postion: Int, item: ResponseSearchItem) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SearchCityDiffCallback())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemSearchBinding =
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemSearchViewHolder(
            itemSearchBinding
        )

    }

    fun submitList(data: List<ResponseSearchItem?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as ItemSearchViewHolder).bind(item)
    }

    inner class ItemSearchViewHolder(val binding: ItemSearchBinding) :
        BaseViewHolder<ResponseSearchItem>(binding) {
        override fun bind(item: ResponseSearchItem) {
            binding.tvCityName.text = item.name
            binding.tvSubCityName.text = item.region
            binding.root.setOnClickListener {
                clickOnResult(adapterPosition, item)
            }
        }
    }

}