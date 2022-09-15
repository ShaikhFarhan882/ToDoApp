package com.example.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.UserAdapter
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.model.UserList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private lateinit var userList : ArrayList<UserList>

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        userList = arrayListOf<UserList>()


        //Getting Current User Data
        getCurrentUserData()

        //Getting all the users from firebase
        getUserList()


        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_addNewTask)
        }



        return binding.root

    }


    private fun getCurrentUserData(){

        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = user.uid

            databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        binding.Name.text = "Name: " + snapshot.child("name").getValue().toString()
                        binding.age.text = "Age: " + snapshot.child("age").getValue().toString()
                        binding.dob.text = "DOB: " + snapshot.child("dob").getValue().toString()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }



    }

    private fun getUserList() {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val users = userSnapshot.getValue(UserList::class.java)
                        userList.add(users!!)
                    }
                   userAdapter = UserAdapter(userList)
                    setRecyclerView(userAdapter)
                }
                else{
                    Toast.makeText(requireContext(),"No user exists",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setRecyclerView(myadapter: UserAdapter) {
        binding.RecView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myadapter
        }
    }

}