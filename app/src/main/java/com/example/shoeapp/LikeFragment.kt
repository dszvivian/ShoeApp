package com.example.shoeapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoeapp.Extensions.toast
import com.example.shoeapp.Models.CartModel
import com.example.shoeapp.Models.LikeModel
import com.example.shoeapp.Models.ShoeDisplayModel
import com.example.shoeapp.databinding.FragmentDetailspageBinding
import com.example.shoeapp.databinding.FragmentLikepageBinding
import com.example.shoeapp.rvadapters.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LikeFragment(): Fragment(R.layout.fragment_likepage), LikedProductOnClickInterface,
    LikedOnClickInterface {

    private lateinit var binding: FragmentLikepageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: LikeAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var likedProductIdList: ArrayList<LikeModel>
    private lateinit var likedProductList: ArrayList<ShoeDisplayModel>


    private var likeDBRef = Firebase.firestore.collection("LikedProducts")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLikepageBinding.bind(view)
        databaseReference = FirebaseDatabase.getInstance().getReference("products")
        auth = FirebaseAuth.getInstance()
        likedProductList = ArrayList()
        likedProductIdList = ArrayList()
        adapter = LikeAdapter(requireContext(),likedProductList,this,this)


        val productLayoutManager = GridLayoutManager(context, 2)
        binding.rvLikedProducts.layoutManager = productLayoutManager
        binding.rvLikedProducts.adapter = adapter


        retrieveLikedProducts()

        displayLikedProducts()

    }

    private fun displayLikedProducts() {


        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                likedProductList.clear()

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ShoeDisplayModel::class.java)

                        for(item in likedProductIdList){
                            if(item.pid == products!!.id){
                                likedProductList.add(products!!)
                            }
                        }
                    }

                    adapter.notifyDataSetChanged()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }

        }


        databaseReference.addValueEventListener(valueEvent)



    }

    private fun retrieveLikedProducts() {

        likeDBRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (item in querySnapshot) {
                    val likedProduct = item.toObject<LikeModel>()

                    if(auth.currentUser!!.uid == likedProduct.uid){
                        likedProductIdList.add(likedProduct)
                    }


                }

            }
            .addOnFailureListener{
                requireActivity().toast(it.localizedMessage!!)
            }




    }

    override fun onClickProduct(item: ShoeDisplayModel) {

    }

    override fun onClickLike(item: ShoeDisplayModel) {
        //todo Remove from Liked Items
    }


}