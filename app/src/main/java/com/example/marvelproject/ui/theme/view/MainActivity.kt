package com.example.marvelproject.ui.theme.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelproject.ui.theme.MarvelProjectTheme
import com.example.marvelproject.ui.theme.view.detailview.DetailView
import com.example.marvelproject.ui.theme.view.listview.ListView
import com.example.marvelproject.ui.theme.view.navigation.MarvelProjectViews
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelProjectTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val vm = hiltViewModel<MainViewModel>()
              /*  val isLoading = vm.loading.value
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)*/

                /*

                SwipeRefresh(state = swipeRefreshState, onRefresh = vm :: getCharacter){

                }*/
                if(vm.error.value.isNotEmpty()){
                    Toast.makeText(context, vm.error.value, Toast.LENGTH_SHORT).show()
                    vm.setErrorAsEmpty()
                }
                NavHost(navController = navController,
                    startDestination = MarvelProjectViews.ListView.route
                ) {
                    composable(MarvelProjectViews.ListView.route) {
                        ListView(vm) {
                            navController.navigate(MarvelProjectViews.DetailView.route)
                        }
                    }

                    composable(MarvelProjectViews.DetailView.route) {
                        DetailView(vm)
                    }
                }
            }
        }
    }
}