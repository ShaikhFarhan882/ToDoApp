package com.example.todoapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddNewTaskBinding
import com.example.todoapp.model.TodoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNewTask : Fragment() {

    private var _binding : FragmentAddNewTaskBinding? = null

    private val binding : FragmentAddNewTaskBinding
    get() = _binding!!

    private lateinit var databaseReference : DatabaseReference


   private val uId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewTaskBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Add"
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_700)));

        binding.apply {

            AddTask.setOnClickListener {
                val task = TodoTitle.text.toString()
                val description = TodoDesc.text.toString()
                when{
                    task.isEmpty() || description.isEmpty() ->{
                        Toast.makeText(requireContext(),"Task cannot be empty",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        databaseReference = FirebaseDatabase.getInstance().getReference("TodoList")

                        val todoList = TodoList(task,description,uId)

                        val key = databaseReference.push().key!!

                        databaseReference.child(key).setValue(todoList).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(requireContext(),"Task Added Successfully",Toast.LENGTH_SHORT).show()
                                /*findNavController().navigate(R.id.action_addNewTask_to_home2)*/
                            }
                            else {
                                Toast.makeText(requireContext(),"Failed to add task",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }


        }



        return binding.root
    }

}