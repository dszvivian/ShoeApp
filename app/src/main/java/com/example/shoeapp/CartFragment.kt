package com.example.shoeapp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoeapp.Models.CartModel
import com.example.shoeapp.databinding.FragmentCartpageBinding
import com.example.shoeapp.rvadapters.CartAdapter

class CartFragment: Fragment(R.layout.fragment_cartpage) {

    private lateinit var binding:FragmentCartpageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCartpageBinding.bind(view)


        val layoutManager = LinearLayoutManager(context)


        val list = ArrayList<CartModel>()
        list.add(CartModel(R.drawable.shoe1, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe2, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe3, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe4, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe5, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe6, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe7, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe8, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe9, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe10, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe1, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe4, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe5, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe6, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe7, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe2, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe3, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe4, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe5, "Nike Air", "100000" , 1))
        list.add(CartModel(R.drawable.shoe1, "Nike Air", "100000" , 1))

        binding.rvCartItems.adapter = CartAdapter(list)
        binding.rvCartItems.layoutManager = layoutManager



    }



}