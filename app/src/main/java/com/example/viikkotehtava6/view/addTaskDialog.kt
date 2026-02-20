package com.example.viikkotehtava6.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.viikkotehtava6.viewmodel.TaskViewModel
import com.example.viikkotehtava6.data.model.Task
import java.util.Date


@Composable
fun addTaskDialog(viewModel: TaskViewModel, onClose:()-> Unit, onUpdate: (Task)-> Unit){
    var title by remember { mutableStateOf("title") }
    var description by remember { mutableStateOf("description") }
    var dueDate by remember { mutableStateOf("2026-2-2") }

    val tasks by viewModel.tasks.collectAsState()


    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("addTask") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("add a title") })
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("add a description") })
                TextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("add a dueDate") },
                    placeholder = { Text("year-month-day") })

            }
        },
        confirmButton = {
            Button(onClick = {
                val newtask = Task(

                    title = title,
                    description = description,
                    priority = 1,
                    dueDate = dueDate,
                    done = false
                )

                viewModel.addTask(newtask)
                onClose()
            }) { Text("save") }
        },
        dismissButton = {
            Button(onClick = {
                onClose()
            }) { Text("cancel") }
        }


    )
}