package com.example.todoapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.SinglerowBinding
import com.example.todoapp.model.UserList
import com.example.todoapp.ui.HomeDirections

class UserAdapter(private val userList: ArrayList<UserList>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SinglerowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = userList[position]


        holder.binding.root.setOnClickListener {
            val uId = userList[position].userId.toString()

            //Navigating to the specific user todolist
            val action = HomeDirections.actionHome2ToTaskList(uId)
            holder.binding.root.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
     return userList.size
    }




    class ViewHolder(val binding : SinglerowBinding) : RecyclerView.ViewHolder(binding.root){

    }
}