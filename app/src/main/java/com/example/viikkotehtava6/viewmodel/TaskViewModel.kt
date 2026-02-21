package com.example.viikkotehtava6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import com.example.viikkotehtava6.data.model.Task

import com.example.viikkotehtava6.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TaskViewModel(private val repository: TaskRepository): ViewModel() {
     private val _tasks: StateFlow<List<Task>> = repository.allTasks.stateIn(
        scope = viewModelScope,
        started= SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    val tasks: StateFlow<List<Task>> = _tasks
    //private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask


    private val _filteredTasks = MutableStateFlow<List<Task>>(emptyList())
    val filteredTasks: StateFlow<List<Task>> = _filteredTasks

    private val _sortByduedate =  MutableStateFlow<List<Task>>(emptyList())
    val sortByduedate: StateFlow<List<Task>> = _sortByduedate
    val addTaskDialogVisible = MutableStateFlow<Boolean>(false)



   fun toggleDone(id: Int){
       viewModelScope.launch {
           val task = tasks.value.find { it.id==id } ?: return@launch
           repository.toggleDone(id,!task.done)

       }
   }
    fun addTask(newTask: Task){
        viewModelScope.launch {

            repository.addTask(newTask)
        }
    }
    fun removeTask(id:Int){
        viewModelScope.launch {
            repository.removeTask(id)

        }
    }

    fun filterByDone(done:Boolean){

        viewModelScope.launch {

            repository.filterByDone(done).collect { filteredList->_filteredTasks.value=filteredList }
        }

    }
    fun sortByDueDate(){
        viewModelScope.launch {
            repository.sortByDueDate().collect { filtered-> _sortByduedate.value=filtered}
        }
    }
    fun updateTask(updated: Task) {
        viewModelScope.launch {
            repository.updateTask(updated)
            _selectedTask.value = null
        }
    }

    fun selectTask(task:Task){
        _selectedTask.value = task
    }
    fun closeDialogWindow(){
        _selectedTask.value = null
    }
}