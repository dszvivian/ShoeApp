package com.example.shoeapp.rvadapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoeapp.Models.LikeModel
import com.example.shoeapp.databinding.ShoedisplaymainItemBinding

class LikeAdapter(private val context: Context,
                  private val list: ArrayList<LikeModel>,
                  private val productClickInterface: LikedProductOnClickInterface,
                  private val likeClickInterface: LikedOnClickInterface,

                  ) : RecyclerView.Adapter<LikeAdapter.ViewHolder>() {


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
        holder.binding.btnLike.backgroundTintList = ColorStateList.valueOf(Color.RED)


        Glide
            .with(context)
            .load(currentItem.imageUrl)
            .into(holder.binding.ivShoeDisplayItem)


        holder.itemView.setOnClickListener {
            productClickInterface.onClickProduct(list[position])
        }

        holder.binding.btnLike.setOnClickListener {
            likeClickInterface.onClickLike(currentItem)
            holder.binding.btnLike.backgroundTintList = ColorStateList.valueOf(Color.WHITE)

            likeClickInterface.onClickLike(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}

interface LikedProductOnClickInterface {
    fun onClickProduct(item: LikeModel)
}

interface LikedOnClickInterface{
    fun onClickLike(item: LikeModel)
}