package com.example.tasklistapplicationkrendomarxece4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklistapplicationkrendomarxece4.databinding.TaskItemBinding

class TaskAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.taskTitle.text = task.name
        holder.binding.taskDate.text = task.date
    }

    override fun getItemCount(): Int = tasks.size

    fun deleteTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }
}
