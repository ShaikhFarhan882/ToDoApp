package com.example.todoapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddNewTaskBinding
import com.example.todoapp.model.TodoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNewTask : Fragment() {

    private var _binding: FragmentAddNewTaskBinding? = null
    private val binding: FragmentAddNewTaskBinding
        get() = _binding!!

    private lateinit var databaseReference: DatabaseReference

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
                addTask(task,description)


    /*
                when {
                    task.isEmpty() || description.isEmpty() -> {
                        Toast.makeText(requireContext(), "Task cannot be empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        databaseReference = FirebaseDatabase.getInstance().getReference("TodoList")

                        val key = databaseReference.push().key!!

                        val userid = uId

                        val todoList = TodoList(task, description, userid)

                        databaseReference.child(key).setValue(todoList).addOnCompleteListener {

                        }.addOnSuccessListener {
                            Toast.makeText(requireContext(),
                                "Task Added Successfully",
                                Toast.LENGTH_SHORT).show()
                            TodoTitle.text.clear()
                            TodoDesc.text.clear()

                        }.addOnFailureListener {
                            Toast.makeText(requireContext(),
                                "Failed to add task",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }*/
            }


        }


        //Clicking send button from the editText.
        binding.TodoDesc.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    val task = binding.TodoTitle.text.toString()
                    val description = binding.TodoDesc.text.toString()
                    addTask(task,description)
                    true
                }
                else -> false
            }
        }



        return binding.root
    }


    private fun addTask(task : String , description : String){

        when {
            task.isEmpty() || description.isEmpty() -> {
                Toast.makeText(requireContext(), "Task cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                databaseReference = FirebaseDatabase.getInstance().getReference("TodoList")

                val key = databaseReference.push().key!!

                val userid = uId

                val todoList = TodoList(task, description, userid)

                databaseReference.child(key).setValue(todoList).addOnCompleteListener {

                }.addOnSuccessListener {
                    Toast.makeText(requireContext(),
                        "Task Added Successfully",
                        Toast.LENGTH_SHORT).show()
                    binding.TodoTitle.text.clear()
                    binding.TodoDesc.text.clear()

                }.addOnFailureListener {
                    Toast.makeText(requireContext(),
                        "Failed to add task",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}