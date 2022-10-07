package com.example.shoeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.shoeapp.Extensions.toast
import com.example.shoeapp.databinding.FragmentSigninBinding
import com.example.shoeapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragmentFragment : Fragment(R.layout.fragment_signin) {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSigninBinding.bind(view)
        auth = FirebaseAuth.getInstance()



        if (auth.currentUser != null) {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_signInFragmentFragment_to_mainFragment)
        }

        binding.btnSignIn.setOnClickListener {

            if (
                binding.etEmailSignIn.text.isNotEmpty() &&
                binding.etPasswordSignIn.text.isNotEmpty()
            ) {


                signinUser(binding.etEmailSignIn.text.toString(),
                    binding.etPasswordSignIn.text.toString())


            } else {
                requireActivity().toast("Some Fields are Empty")
            }
        }


        binding.tvNavigateToSignUp.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_signInFragmentFragment_to_signUpFragment)
        }


    }

    private fun signinUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    requireActivity().toast("Sign In Successful")

                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_signInFragmentFragment_to_mainFragment)
                } else {
                    requireActivity().toast(task.exception!!.localizedMessage!!)

                }


            }

    }


}