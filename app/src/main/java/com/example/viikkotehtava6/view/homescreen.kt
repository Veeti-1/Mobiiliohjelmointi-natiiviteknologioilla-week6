package com.example.viikkotehtava6.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava6.viewmodel.TaskViewModel

import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun viewModelHomeScreen(viewModel: TaskViewModel, onNavigateCalendar: () -> Unit = {}, onNavigateSettings:()-> Unit={}, onNavigateWeather:()->Unit={}) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    val addTaskFlag by viewModel.addTaskDialogVisible.collectAsState()
    val filteredTasks by viewModel.filteredTasks.collectAsState()
    val sortedbydueDate by viewModel.sortByduedate.collectAsState()

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = { Text("Tasks") },
            actions = {
                IconButton(onClick = onNavigateCalendar) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = "Show calendar"
                    )
                }
                IconButton(onClick = onNavigateSettings) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Show Settings"

                    )
                }
                IconButton(onClick = onNavigateWeather) {
                    Icon(
                        Icons.Default.Cloud,
                        contentDescription = "Show Weather"
                    )
                }
            }
        )
        Row(
            modifier = Modifier.padding(top = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {


            Button(
                onClick = {


                    viewModel.filterByDone(done = true)


                }) { Text(text = "Show only done tasks") }

            Button(

                onClick = {


                    viewModel.sortByDueDate()
                }) { Text(text = "Sort by dueDate") }
            Button(
                onClick = {
                    viewModel.addTaskDialogVisible.value = true
                }

            ) { Text(text = "Add") }

        }
        val displayTasks = if (filteredTasks.isEmpty() && sortedbydueDate.isEmpty()){
            tasks
        }else if(!filteredTasks.isEmpty() && sortedbydueDate.isEmpty()){
            filteredTasks
        }else{
            sortedbydueDate
    }

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {


            items(displayTasks)

            { task ->

                Row(modifier = Modifier.clickable { viewModel.selectTask(task) }) {
                    Column(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Title: ${task.title}"


                            )
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { viewModel.toggleDone(task.id) }
                            )
                        }

                        Text(

                            text = "${task.description}",
                            modifier = Modifier.width(120.dp)

                        )
                    }


                }

            }



        }
    }
    if(selectedTask!=null){
        DetailDialog(
            viewModel = viewModel,
            task = selectedTask!!,
            onClose = { viewModel.closeDialogWindow() },
            onUpdate = { viewModel.updateTask(it) })
    }
    if(addTaskFlag){
        addTaskDialog(
            viewModel = viewModel,
            onClose = { viewModel.addTaskDialogVisible.value = false },
            onUpdate = { viewModel.addTask(it) })
    }
}