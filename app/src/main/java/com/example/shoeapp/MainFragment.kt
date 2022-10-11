package com.example.shoeapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoeapp.Extensions.toast
import com.example.shoeapp.Models.LikeModel
import com.example.shoeapp.Models.ShoeDisplayModel
import com.example.shoeapp.databinding.FragmentMainpageBinding
import com.example.shoeapp.rvadapters.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment(R.layout.fragment_mainpage),
    CategoryOnClickInterface,
    ProductOnClickInterface, LikeOnClickInterface {


    private lateinit var binding: FragmentMainpageBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var productList: ArrayList<ShoeDisplayModel>
    private lateinit var categoryList: ArrayList<String>
    private lateinit var productsAdapter: ShoeDisplayAdapter
    private lateinit var categoryAdapter: MainCategoryAdapter
    private lateinit var auth: FirebaseAuth
    private var likeDBRef = Firebase.firestore.collection("LikedProducts")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainpageBinding.bind(view)
        categoryList = ArrayList()
        productList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("products")
        auth = FirebaseAuth.getInstance()


        // region implements category Recycler view

        categoryList.add("Trending")
        binding.rvMainCategories.setHasFixedSize(true)
        val categoryLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvMainCategories.layoutManager = categoryLayoutManager
        categoryAdapter = MainCategoryAdapter(categoryList, this)
        binding.rvMainCategories.adapter = categoryAdapter
        setCategoryList()

        // endregion implements category Recycler view


        // region implements products Recycler view

        val productLayoutManager = GridLayoutManager(context, 2)
        productsAdapter = ShoeDisplayAdapter(requireContext(), productList, this,this)
        binding.rvMainProductsList.layoutManager = productLayoutManager
        binding.rvMainProductsList.adapter = productsAdapter
        setProductsData()
        // endregion implements products Recycler view


        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_mainFragment_self)
                    true
                }
                R.id.likeFragment -> {
//                    requireActivity().toast("Like Page coming Soon")
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_mainFragment_to_likeFragment2)
                    true
                }
                R.id.cartFragment -> {

                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_mainFragment_to_cartFragment)

                    true
                }
                R.id.profileFragment -> {

                    auth.signOut()
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_mainFragment_to_signInFragmentFragment)
                    true
                }
                else -> false

            }

        }


    }

    private fun setCategoryList() {

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                categoryList.clear()
                categoryList.add("Trending")

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ShoeDisplayModel::class.java)

                        categoryList.add(products!!.brand!!)

                    }

                    categoryAdapter.notifyDataSetChanged()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }

        }


        databaseReference.addValueEventListener(valueEvent)




    }


    private fun setProductsData() {

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                productList.clear()

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ShoeDisplayModel::class.java)
                        productList.add(products!!)
                    }

                    productsAdapter.notifyDataSetChanged()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }

        }

        databaseReference.addValueEventListener(valueEvent)

    }

    override fun onClickCategory(button: Button) {
        binding.tvMainCategories.text = button.text

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                productList.clear()

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ShoeDisplayModel::class.java)

                        if (products!!.brand == button.text) {
                            productList.add(products)
                        }

                        if (button.text == "Trending") {
                            productList.add(products)
                        }

                    }

                    productsAdapter.notifyDataSetChanged()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }

        }

        databaseReference.addValueEventListener(valueEvent)


    }


    override fun onClickProduct(item: ShoeDisplayModel) {

        val direction = MainFragmentDirections
            .actionMainFragmentToDetailsFragment(
                item.id!!
            )

        Navigation.findNavController(requireView())
            .navigate(direction)


    }

    override fun onClickLike(item: ShoeDisplayModel) {

        likeDBRef
            .add(LikeModel(item.id , auth.currentUser!!.uid , item.brand , item.description , item.imageUrl , item.name ,item.price))
            .addOnSuccessListener {
                requireActivity().toast("Added to Liked Items")
            }
            .addOnFailureListener {
                requireActivity().toast("Failed to Add to Liked")
            }

    }



}




