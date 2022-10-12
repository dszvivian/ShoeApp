package com.example.shoeapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoeapp.Extensions.toast
import com.example.shoeapp.Models.CartModel
import com.example.shoeapp.databinding.FragmentCartpageBinding
import com.example.shoeapp.rvadapters.CartAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class CartFragment : Fragment(R.layout.fragment_cartpage), CartAdapter.OnLongClickRemove {

    private lateinit var binding: FragmentCartpageBinding
    private lateinit var cartList: ArrayList<CartModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: CartAdapter
    private var subTotalPrice = 0
    private var totalPrice = 240

    private var orderDatabaseReference = Firebase.firestore.collection("orders")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCartpageBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.cartActualToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }


        val layoutManager = LinearLayoutManager(context)


        cartList = ArrayList()

        retrieveCartItems()



        adapter = CartAdapter(requireContext(),cartList ,this)
        binding.rvCartItems.adapter = adapter
        binding.rvCartItems.layoutManager = layoutManager






        binding.btnCartCheckout.setOnClickListener {

            requireActivity().toast("Whooooa!! You've Ordered Products worth ${totalPrice}\n Your Product will be delivered in next 7 days")
            cartList.clear()
            binding.tvLastSubTotalprice.text ="0"
            binding.tvLastTotalPrice.text ="Min 1 product is Required"
            binding.tvLastTotalPrice.setTextColor(Color.RED)
            // TODO: remove the data of the Products from the fireStore after checkout or insert a boolean isDelivered
            adapter.notifyDataSetChanged()
        }


    }




    private fun retrieveCartItems() {

        orderDatabaseReference
            .whereEqualTo("uid",auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (item in querySnapshot) {
                    val cartProduct = item.toObject<CartModel>()


                        cartList.add(cartProduct)
                        subTotalPrice += cartProduct.price!!.toInt()
                        totalPrice += cartProduct.price!!.toInt()
                        binding.tvLastSubTotalprice.text = subTotalPrice.toString()
                        binding.tvLastTotalPrice.text = totalPrice.toString()
                        binding.tvLastSubTotalItems.text = "SubTotal Items(${cartList.size})"
                        adapter.notifyDataSetChanged()


                }

            }
            .addOnFailureListener{
                requireActivity().toast(it.localizedMessage!!)
            }


    }

    override fun onLongRemove(item: CartModel , position:Int) {


        orderDatabaseReference
            .whereEqualTo("uid",item.uid)
            .whereEqualTo("pid",item.pid)
            .whereEqualTo("size",item.size)
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (item in querySnapshot){
                    orderDatabaseReference.document(item.id).delete()
                    cartList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    requireActivity().toast("Removed Successfully!!!")
                }

            }
            .addOnFailureListener {
                requireActivity().toast("Failed to remove")
            }



    }




}