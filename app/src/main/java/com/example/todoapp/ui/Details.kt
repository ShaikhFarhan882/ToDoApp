package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailsBinding
import com.example.todoapp.model.UserList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Details : Fragment() {
    private var _binding : FragmentDetailsBinding? = null
    private val binding : FragmentDetailsBinding get() = _binding!!
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        binding.apply {
            saveDetails.setOnClickListener {
                saveDetails()
            }

            dateOfBirth.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->

                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        dateOfBirth.setText(dat)
                    },
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            }
        }
        return binding.root
    }

    private fun saveDetails(){
        val name = binding.detailsName.text.toString()
        val age = binding.detailsAge.text.toString()
        val dob = binding.dateOfBirth.text.toString()

        when{
            name.isEmpty() || age.isEmpty() || dob.isEmpty() ->{
                Toast.makeText(requireContext(),"Enter Details",Toast.LENGTH_SHORT).show()
            }


            else -> {
                val uId = FirebaseAuth.getInstance().currentUser!!.uid
                val userList = UserList(name,age,dob,uId)

                databaseReference.child(uId).setValue(userList).addOnCompleteListener {
                    Toast.makeText(requireContext(),"Saved Successfully",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_details_to_home2)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}