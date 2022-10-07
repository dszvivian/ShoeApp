package com.example.shoeapp.rvadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.shoeapp.databinding.CategorymainItemBinding

class MainCategoryAdapter(
    private val list: ArrayList<String>,
    val onClickCategory: CategoryOnClickInterface
    ): RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>() {




    class ViewHolder(val binding : CategorymainItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategorymainItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.btnItemCategory.text = list[position]

        holder.binding.btnItemCategory.setOnClickListener {
            onClickCategory.onClickCategory(holder.binding.btnItemCategory)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}

interface CategoryOnClickInterface{
    fun  onClickCategory(button:Button)
}