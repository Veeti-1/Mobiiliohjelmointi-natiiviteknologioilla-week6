package com.example.viikkotehtava6.data.repository

import com.example.viikkotehtava6.data.model.Task
import com.example.viikkotehtava6.data.local.TaskDao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty

class TaskRepository(private val taskDao: TaskDao){
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()



    suspend fun addTask(newTask: Task){
        return taskDao.insertTask(newTask)
    }
    suspend fun toggleDone(id:Int, completed:Boolean){
        taskDao.toggleDone(id,completed)
    }
    suspend fun removeTask(id:Int){
        taskDao.removeTask(id)
    }
    fun filterByDone(completed:Boolean): Flow<List<Task>>{

        return taskDao.sortByDone(completed)
    }

     fun sortByDueDate(): Flow<List<Task>>{
        return taskDao.sortByDueDate()
    }
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}