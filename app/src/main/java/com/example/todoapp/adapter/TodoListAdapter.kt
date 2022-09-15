package com.example.todoapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.SinglerowBinding
import com.example.todoapp.databinding.SinglerowTodolistBinding
import com.example.todoapp.model.TodoList
import com.example.todoapp.model.UserList
import com.example.todoapp.ui.HomeDirections

class TodoListAdapter(private val todolist: ArrayList<TodoList>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SinglerowTodolistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.todolist = todolist[position]

    }

    override fun getItemCount(): Int {
     return todolist.size
    }




    class ViewHolder(val binding : SinglerowTodolistBinding) : RecyclerView.ViewHolder(binding.root){

    }
}