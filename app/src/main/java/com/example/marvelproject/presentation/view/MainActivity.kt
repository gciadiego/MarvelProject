package com.example.marvelproject.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.marvelproject.ui.theme.MarvelProjectTheme
import com.example.marvelproject.presentation.view.detailview.DetailView
import com.example.marvelproject.presentation.view.listview.ListView
import com.example.marvelproject.presentation.view.navigation.MarvelProjectViews
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelProjectTheme {
                val navController = rememberNavController()
                //ViewModel injection with Koin
                val vmKoin: MainViewModel by viewModel()

                NavHost(navController = navController, startDestination = MarvelProjectViews.ListView.route){
                    composable(MarvelProjectViews.ListView.route) {
                        ListView(vmKoin){
                            navController.navigate(MarvelProjectViews.DetailView.route)
                        }
                    }

                    composable(MarvelProjectViews.DetailView.route) {
                        DetailView(vmKoin){
                            navController.navigate(MarvelProjectViews.ListView.route){
                                navOptions {
                                    popUpTo(MarvelProjectViews.ListView.route)
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}