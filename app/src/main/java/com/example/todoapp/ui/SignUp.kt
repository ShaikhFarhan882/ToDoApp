package com.example.todoapp.ui

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUp : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding.apply {


            btSignup.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()

                when {
                    email.isEmpty() -> {
                        signUpEmail.requestFocus()
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
                    }

                    password.isEmpty() || password.length < 6 -> {
                        signUpPassword.requestFocus()
                    }

                    else -> {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                            Toast.makeText(requireContext(), "Completed", Toast.LENGTH_SHORT).show()
                        }
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(),
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(),
                                    "Login to continue",
                                    Toast.LENGTH_SHORT).show()

                                signUpEmail.setText(" ")
                                signUpPassword.setText(" ")
                            }
                            .addOnCanceledListener {
                                Toast.makeText(requireContext(),
                                    "Failed to create account",
                                    Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }

            signUpToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signUp_to_login)
            }

        }

        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}