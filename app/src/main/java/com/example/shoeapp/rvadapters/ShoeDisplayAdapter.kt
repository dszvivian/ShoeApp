package com.example.shoeapp.rvadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoeapp.Models.ShoeDisplayModel
import com.example.shoeapp.databinding.ShoedisplaymainItemBinding

class ShoeDisplayAdapter(
   private val context:Context ,
   private val list: List<ShoeDisplayModel>,
   private val productClickInterface: ProductOnClickInterface,

    ) : RecyclerView.Adapter<ShoeDisplayAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ShoedisplaymainItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ShoedisplaymainItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.tvNameShoeDisplayItem.text = "${currentItem.brand} ${currentItem.name}"
        holder.binding.tvPriceShoeDisplayItem.text = "₹${currentItem.price}"

        Glide
            .with(context)
            .load(currentItem.imageUrl)
            .into(holder.binding.ivShoeDisplayItem)


        holder.itemView.setOnClickListener {
            productClickInterface.onClickProduct(list[position])
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}

interface ProductOnClickInterface {
    fun onClickProduct(item: ShoeDisplayModel)
}