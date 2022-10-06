package com.example.shoeapp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.shoeapp.databinding.FragmentDetailspageBinding

class DetailsFragment: Fragment(R.layout.fragment_detailspage) {

    private lateinit var binding : FragmentDetailspageBinding

    private val args : DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailspageBinding.bind(view)

        val name = args.productName
        val price = "₹${args.price}"
        val rId = args.resourceId


        binding.tvDetailsProductName.text = name
        binding.tvDetailsProductPrice.text = price
        binding.ivDetails.setImageResource(rId)

        binding.btnDetailsAddToCart.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_cartFragment)
        }







    }

}