package com.example.shoeapp.rvadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoeapp.Models.CartModel
import com.example.shoeapp.databinding.CartproductItemBinding

class CartAdapter(val list:List<CartModel>):RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    class ViewHolder(val binding:CartproductItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartproductItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = list[position]

        holder.binding.ivCartProduct.setImageResource(currentItem.imageId)
        holder.binding.tvCartProductName.text = currentItem.productName
        holder.binding.tvCartProductPrice.text = currentItem.productPrice

        var count = holder.binding.tvCartItemCount.text.toString().toInt()

        holder.binding.btnCartItemAdd.setOnClickListener {
            count++
            currentItem.itemCount++
            holder.binding.tvCartItemCount.text = count.toString()
        }

        holder.binding.btnCartItemMinus.setOnClickListener {
            count--
            currentItem.itemCount++
            holder.binding.tvCartItemCount.text = count.toString()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}