package com.example.tasklistapplicationkrendomarxece4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklistapplicationkrendomarxece4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(tasks)
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener {
            val taskText = binding.taskInput.text.toString()
            if (taskText.isEmpty()) {
                binding.taskInput.error = "Task cannot be empty"
                return@setOnClickListener
            }

            val task = Task(taskText, getCurrentDate())
            tasks.add(task)
            taskAdapter.notifyItemInserted(tasks.size - 1)
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
            binding.taskInput.text.clear()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = tasks[position]

                AlertDialog.Builder(this@MainActivity)
                    .setMessage("Are you sure you want to delete '${task.name}'?")
                    .setPositiveButton("Delete") { _, _ ->
                        taskAdapter.deleteTask(position)
                        Snackbar.make(binding.recyclerView, "Task deleted", Snackbar.LENGTH_LONG).show()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        taskAdapter.notifyItemChanged(position)
                        dialog.dismiss()
                    }
                    .show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}