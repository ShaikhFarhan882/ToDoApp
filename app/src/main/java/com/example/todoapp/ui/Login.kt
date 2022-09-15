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
import com.example.todoapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.loginToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUp)
        }

        binding.btLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.loginEmail.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
                }

                password.isEmpty() || password.length < 6 -> {
                    binding.loginPassword.requestFocus()
                }
                else -> {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                    }
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(),
                                "Welcome",
                                Toast.LENGTH_SHORT).show()
                            binding.loginEmail.text!!.clear()
                            binding.loginPassword.text!!.clear()
                            ifUserExists()

                        }.addOnFailureListener {
                            Toast.makeText(requireContext(),
                                "Invalid Email or Password",
                                Toast.LENGTH_SHORT).show()
                        }
                        .addOnCanceledListener {
                            Toast.makeText(requireContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }


        }

        binding.forgotPwd.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            when{
                email.isEmpty() -> {
                    Toast.makeText(requireContext(),"Enter email address to continue",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(requireContext(),"Reset password link send",Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(requireContext(),"Failed to send link",Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

        return binding.root


    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            findNavController().navigate(R.id.action_login_to_home2)
        }
    }


    private fun ifUserExists() {

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = user.uid

            databaseReference.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        findNavController().navigate(R.id.action_login_to_home2)
                    } else {
                        findNavController().navigate(R.id.action_login_to_details)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


