package com.example.todoapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "UsersList"
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_700)));

        userList = arrayListOf()

        userAdapter = UserAdapter(userList)


        //Getting Current User Data
        getCurrentUserData()

        //Getting all the users from firebase
        getUserList()


        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_addNewTask)
        }

        binding.switcher.setOnClickListener {
            if(binding.switcher.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.mymenu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.logout -> {
                logOutUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun logOutUser(){
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_home2_to_login)
    }


    private fun getCurrentUserData(){

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
            layoutManager = LinearLayoutManager(activity)
            adapter = myadapter
        }
    }

}