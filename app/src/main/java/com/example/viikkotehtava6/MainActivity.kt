package com.example.viikkotehtava6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkotehtava6.data.local.AppDatabase
import com.example.viikkotehtava6.data.repository.TaskRepository
import com.example.viikkotehtava6.navigation.HOME_ROUTE
import com.example.viikkotehtava6.viewmodel.TaskViewModel
import com.example.viikkotehtava6.navigation.CALENDAR_ROUTE
import com.example.viikkotehtava6.navigation.SETTINGS_ROUTE
import com.example.viikkotehtava6.view.CalendarScreen
import com.example.viikkotehtava6.view.SettingsScreen
import com.example.viikkotehtava6.view.viewModelHomeScreen

class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }


    private val repository by lazy {
        TaskRepository(database.TaskDao())
    }
    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()


            NavHost(
                startDestination = HOME_ROUTE,
                navController = navController

            ) {
                composable(HOME_ROUTE) {
                    viewModelHomeScreen(
                        viewModel = viewModel,
                        onNavigateCalendar = {
                            navController.navigate(CALENDAR_ROUTE)
                        }, onNavigateSettings = {
                            navController.navigate(SETTINGS_ROUTE)
                        }
                    )


                }
                composable(CALENDAR_ROUTE) {
                    CalendarScreen(
                        viewModel = viewModel,
                        onNavigateHome = {
                            navController.navigate(HOME_ROUTE)
                        }
                    )
                }
                composable(SETTINGS_ROUTE) {
                    SettingsScreen(onNavigateHome = {
                        navController.navigate(HOME_ROUTE)
                    })
                }


            }
        }
    }
}


