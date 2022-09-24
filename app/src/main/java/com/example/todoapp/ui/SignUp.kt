package com.example.todoapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSignUpBinding
import com.google.android.gms.common.api.internal.RegisterListenerMethod
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

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Signup"
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_700)));

        binding.apply {

            btSignup.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()

                register(email,password)

         /*       when {
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

                                signUpEmail.text!!.clear()
                                signUpPassword.text!!.clear()

                            }
                            .addOnCanceledListener {
                                Toast.makeText(requireContext(),
                                    "Failed to create account",
                                    Toast.LENGTH_SHORT).show()
                            }
                    }
                }*/

            }

            signUpToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signUp_to_login)
            }

        }

        //Clicking send button from the editText.
        binding.signUpPassword.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    val email = binding.signUpEmail.text.toString()
                    val password = binding.signUpPassword.text.toString()
                    register(email,password)
                    true
                }
                else -> false
            }
        }





        return binding.root


    }



    private fun register (email : String,password: String){

        when {
            email.isEmpty() -> {
                binding.signUpEmail.requestFocus()
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
            }

            password.isEmpty() || password.length < 6 -> {
                binding.signUpPassword.requestFocus()
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

                        binding.signUpEmail.text!!.clear()
                        binding.signUpPassword.text!!.clear()

                    }
                    .addOnCanceledListener {
                        Toast.makeText(requireContext(),
                            "Failed to create account",
                            Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}