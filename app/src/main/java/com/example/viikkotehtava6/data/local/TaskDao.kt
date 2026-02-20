package com.example.viikkotehtava6.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.viikkotehtava6.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao{
    @Insert
    suspend fun insertTask(vararg tasks: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>
    @Query("UPDATE tasks SET done = :completed WHERE id = :id")
    suspend fun toggleDone(id: Int, completed: Boolean)

    @Query("SELECT * FROM tasks WHERE done = :completed")
    fun sortByDone(completed: Boolean):Flow<List<Task>>
    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun removeTask(taskId: Int)
    @Query("SELECT * FROM tasks ORDER BY dueDate")
    fun sortByDueDate(): Flow<List<Task>>

    @Update
    suspend fun updateTask(task: Task)
}