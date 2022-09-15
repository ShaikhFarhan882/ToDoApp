package com.example.todoapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.TodoListAdapter
import com.example.todoapp.databinding.FragmentTaskListBinding
import com.example.todoapp.model.TodoList
import com.google.firebase.database.*


class TaskList : Fragment() {

    private var _binding : FragmentTaskListBinding? = null

    private val binding : FragmentTaskListBinding get() = _binding!!

    private val args : TaskListArgs by navArgs()

    private lateinit var todolist : ArrayList<TodoList>

    private lateinit var todolistAdapter : TodoListAdapter

    private lateinit var databaseReference: DatabaseReference





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       _binding = FragmentTaskListBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "TaskList"
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_700)));

        val uId = args.userlist

        todolist = arrayListOf()


        fun getUserTodoList(){
            databaseReference = FirebaseDatabase.getInstance().getReference("TodoList")

            databaseReference.orderByChild("userId").equalTo(uId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val todoList = userSnapshot.getValue(TodoList::class.java)
                            todolist.add(todoList!!)
                        }
                        todolistAdapter = TodoListAdapter(todolist)
                        setRecyclerView(todolistAdapter)
                    }
                    else{
                        Toast.makeText(requireContext(),"No data exists", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        getUserTodoList()


        return binding.root
    }


    private fun setRecyclerView(myadapter: TodoListAdapter) {
        binding.RecView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myadapter
        }
    }




}

