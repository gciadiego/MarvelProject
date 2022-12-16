package com.example.marvelproject.ui.theme.view.navigation

sealed class MarvelProjectViews(val route: String){
    object ListView: MarvelProjectViews("list_view")
    object DetailView: MarvelProjectViews("detail_view")
}
