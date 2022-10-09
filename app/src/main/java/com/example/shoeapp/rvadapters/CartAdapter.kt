package com.example.shoeapp.rvadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoeapp.Models.CartModel
import com.example.shoeapp.databinding.CartproductItemBinding

class CartAdapter(
    private val context : Context,
    private val list:List<CartModel>
    ):RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    class ViewHolder(val binding:CartproductItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartproductItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = list[position]

        Glide
            .with(context)
            .load(currentItem.imageUrl)
            .into(holder.binding.ivCartProduct)


        holder.binding.tvCartProductName.text = currentItem.name
        holder.binding.tvCartProductPrice.text = "₹${currentItem.price}"
        holder.binding.tvCartItemCount.text = currentItem.quantity.toString()
        holder.binding.tvCartProductSize.text = currentItem.size

        var count = holder.binding.tvCartItemCount.text.toString().toInt()

        holder.binding.btnCartItemAdd.setOnClickListener {
//            count++
            // TODO: Update Quantity in Database also
//            holder.binding.tvCartItemCount.text = count.toString()

        }

        holder.binding.btnCartItemMinus.setOnClickListener {
//            count--
            // TODO: Update Quantity in Database also
//            holder.binding.tvCartItemCount.text = count.toString()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}