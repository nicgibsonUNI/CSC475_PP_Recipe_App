package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.viewmodel.RecipeViewModel
import com.example.recipeapp.screens.FavoritesScreen
import com.example.recipeapp.screens.ProfileScreen
import com.example.recipeapp.screens.RecipeAppHomeScreen
import com.example.recipeapp.screens.RecipeDetailScreen
import com.example.recipeapp.screens.SearchScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val recipeViewModel: RecipeViewModel = viewModel()

            NavHost(navController = navController, startDestination = "home") {
                // Home Screen
                composable("home") {
                    RecipeAppHomeScreen(navController, recipeViewModel)
                }

                // Recipe Detail Screen
                composable(
                    route = "recipeDetail/{recipeId}",
                    arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt("recipeId")
                    RecipeDetailScreen(recipeId, recipeViewModel, navController)
                }

                // Generic Search function
                composable("search") {
                    SearchScreen(navController, recipeViewModel, "All")  // Default mealType is "All"
                }

                // Search function for mealType buttons
                composable("searchResults/{mealType}") { backStackEntry ->
                    val mealType = backStackEntry.arguments?.getString("mealType")
                    if (mealType != null) {
                        SearchScreen(navController, viewModel = recipeViewModel, mealType = mealType)
                    }
                }

                // Favorite Screen
                composable("favorites") {
                    FavoritesScreen(recipeViewModel, navController)
                }

                // Profile Screen
                composable("profile") {
                    ProfileScreen(navController)
                }
            }
        }
    }
}








