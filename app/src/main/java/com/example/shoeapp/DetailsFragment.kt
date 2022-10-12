package com.example.shoeapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shoeapp.Extensions.toast
import com.example.shoeapp.Models.ProductOrderModel
import com.example.shoeapp.Models.ShoeDisplayModel
import com.example.shoeapp.databinding.FragmentDetailspageBinding
import com.example.shoeapp.rvadapters.SizeAdapter
import com.example.shoeapp.rvadapters.SizeOnClickInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment : Fragment(R.layout.fragment_detailspage), SizeOnClickInterface {

    private lateinit var binding: FragmentDetailspageBinding
    private lateinit var productDatabaseReference: DatabaseReference
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var auth: FirebaseAuth
    private val args: DetailsFragmentArgs by navArgs()

    private val orderDatabaseReference = Firebase.firestore.collection("orders")

    private lateinit var currentUID :  String
    private lateinit var orderImageUrl:String
    private lateinit var orderName:String
    private var orderSize:String?  = null
    private var orderQuantity:Int  = 1
    private lateinit var orderPrice:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailspageBinding.bind(view)

        productDatabaseReference = FirebaseDatabase.getInstance().getReference("products")

        val productId = args.productId
        auth = FirebaseAuth.getInstance()

        currentUID = auth.currentUser!!.uid

        binding.detailActualToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }



        // region implements firebase product display
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ShoeDisplayModel::class.java)

                        if (products!!.id == productId) {
                            Glide
                                .with(requireContext())
                                .load(products.imageUrl)
                                .into(binding.ivDetails)

                            orderImageUrl = products.imageUrl!!
                            orderName = products.name!!
                            orderPrice = products.price!!

                            binding.tvDetailsProductPrice.text = "₹${products.price}"
                            binding.tvDetailsProductName.text = "${products.brand} ${products.name}"
                            binding.tvDetailsProductDescription.text = products.description
                        }


                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {
                requireActivity().toast(error.message)
            }

        }


        productDatabaseReference.addValueEventListener(valueEvent)

        // endregion implements firebase product display

        // region implements size recycler view

        val sizeList = ArrayList<String>()
        sizeList.add("5")
        sizeList.add("6")
        sizeList.add("7")
        sizeList.add("8")
        sizeList.add("9")
        sizeList.add("10")


        sizeAdapter = SizeAdapter(requireContext() , sizeList , this)
        binding.rvSelectSize.adapter = sizeAdapter

        // endregion implements size recycler view



        binding.btnDetailsAddToCart.setOnClickListener {

            // TODO: Add Data to FireBase FireStore Database

            val orderedProduct = ProductOrderModel(currentUID,productId,orderImageUrl,orderName,orderSize,orderQuantity,orderPrice)

            if(orderSize.isNullOrBlank()){
                requireActivity().toast("Select Size")
            }else{
                addDataToOrdersDatabase(orderedProduct)

                Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_cartFragment)
            }


        }

    }

    private fun addDataToOrdersDatabase(orderedProduct: ProductOrderModel) {

        orderDatabaseReference.add(orderedProduct).addOnCompleteListener{task ->
            if(task.isSuccessful){
                requireActivity().toast("Order Successfully Delivered")
            }else{
                requireActivity().toast(task.exception!!.localizedMessage!!)
            }
        }

    }

    override fun onClickSize(button: Button , position :Int) {
        orderSize = button.text.toString()
        requireActivity().toast("Size ${button.text} Selected")
    }

}