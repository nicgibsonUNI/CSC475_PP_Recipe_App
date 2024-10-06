package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipeapp.components.BottomNavigationBar
import com.example.recipeapp.components.TopBar
import com.example.recipeapp.ui.theme.RecipeAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent() // Call the composable function inside setContent
        }
    }
}

@Composable
fun MainActivityContent() {
    RecipeAppTheme {
        val navController = rememberNavController()
        val recipeViewModel: RecipeViewModel = viewModel()

        Scaffold(
            topBar = { TopBar() },  // TopBar Composable
            bottomBar = { BottomNavigationBar(navController) }, // Optional Bottom Navigation Bar
        ) { innerPadding ->
            // Apply padding from the scaffold
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)  // Ensure NavHost respects padding
            ) {
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
                    if (recipeId != null) {
                        RecipeDetailScreen(
                            recipeId = recipeId,
                            recipeViewModel = recipeViewModel,
                            navController = navController
                        )
                    } else {
                        Text("Error: Recipe not found")
                    }
                }

                // Search Screen
                composable("search") {
                    SearchScreen(navController, recipeViewModel, "All")  // Default mealType is "All"
                }

                // Search function for mealType buttons
                composable("searchResults/{mealType}") { backStackEntry ->
                    val mealType = backStackEntry.arguments?.getString("mealType")
                    if (mealType != null) {
                        SearchScreen(navController, recipeViewModel, mealType)
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













