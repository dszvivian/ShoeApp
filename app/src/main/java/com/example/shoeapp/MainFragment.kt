package com.example.shoeapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoeapp.Models.ShoeDisplayModel
import com.example.shoeapp.databinding.FragmentMainpageBinding
import com.example.shoeapp.rvadapters.CategoryOnClickInterface
import com.example.shoeapp.rvadapters.MainCategoryAdapter
import com.example.shoeapp.rvadapters.ProductOnClickInterface
import com.example.shoeapp.rvadapters.ShoeDisplayAdapter
import com.google.firebase.database.*

class MainFragment : Fragment(R.layout.fragment_mainpage),
    CategoryOnClickInterface,
    ProductOnClickInterface {


    private lateinit var binding: FragmentMainpageBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var productList:ArrayList<ShoeDisplayModel>
    private lateinit var productsAdapter: ShoeDisplayAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainpageBinding.bind(view)


        // region implements category Recycler view

        val list = listOf("Adidas",
            "Nike",
            "Puma",
            "Reebok",
            "Air Jordan",
            "Predator",
            "Asics",
            "Woodland",
            "Nivea")
        binding.rvMainCategories.setHasFixedSize(true)
        val categoryLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvMainCategories.layoutManager = categoryLayoutManager
        binding.rvMainCategories.adapter = MainCategoryAdapter(list, this)

        // endregion implements category Recycler view


        // region implements products Recycler view

        productList = ArrayList<ShoeDisplayModel>()

//        productList.add(ShoeDisplayModel(R.drawable.shoe1, "Nike Air", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe2, "Nike Cammando", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe3, "Adidas Feynman Edition", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe4, "Nivea Sprint", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe5, "Nike Training", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe6, "Adidas Predator", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe7, "Nike Gladiator", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe8, "Rebook Reborn", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe9, "Sangam RichMan", "100000"))
//        productList.add(ShoeDisplayModel(R.drawable.shoe10, "Mathura Air Pro", "100000"))


        val productLayoutManager = GridLayoutManager(context, 2)
        productsAdapter = ShoeDisplayAdapter(requireContext() ,productList, this)
        binding.rvMainProductsList.layoutManager = productLayoutManager
        binding.rvMainProductsList.adapter = productsAdapter




        // endregion implements products Recycler view


        databaseReference = FirebaseDatabase.getInstance().getReference("products")

        setData()

    }

    private fun setData() {

        val valueEvent = object:ValueEventListener{
            override fun onDataChange( snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for ( dataSnapshot in snapshot.children){
                        val products = dataSnapshot.getValue(ShoeDisplayModel::class.java)
                        productList.add(products!!)
                    }



                    productsAdapter.notifyDataSetChanged()
                }



            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error",Toast.LENGTH_SHORT).show()
            }

        }


        databaseReference.addValueEventListener(valueEvent)


    }

    override fun onClickCategory(button: Button) {
        binding.tvMainCategories.text = button.text
    }

    override fun onClickProduct(item: ShoeDisplayModel) {

        val direction = MainFragmentDirections
            .actionMainFragmentToDetailsFragment(
                item.name!!,
                item.price!!,
                R.drawable.shoe10
            )

        Navigation.findNavController(requireView())
            .navigate(direction)


    }


}


