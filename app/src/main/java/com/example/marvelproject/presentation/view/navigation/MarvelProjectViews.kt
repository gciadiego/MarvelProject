package com.example.marvelproject.presentation.view.navigation

sealed class MarvelProjectViews(val route: String){
    object ListView: MarvelProjectViews("list_view")
    object DetailView: MarvelProjectViews("detail_view")
}
