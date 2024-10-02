package com.example.recipeapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipeapp.components.BottomNavigationBar
import com.example.recipeapp.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(recipeId: Int?, recipeViewModel: RecipeViewModel, navController: NavController) {
    // Log the recipeId being passed
    Log.d("RecipeDetailScreen", "Received recipeId: $recipeId")

    if (recipeId == null) {
        Log.e("RecipeDetailScreen", "Recipe ID is null")
        Text(text = "Invalid recipe ID", modifier = Modifier.padding(16.dp))
        return
    }

    // Observe recipes from the ViewModel
    val recipes by recipeViewModel.allRecipes.observeAsState(emptyList())

    // Log the size of the recipes list
    Log.d("RecipeDetailScreen", "Total recipes available: ${recipes.size}")

    // Trigger recipe search and log when recipes are loaded
    LaunchedEffect(recipes) {
        val recipe = recipes.find { it.id == recipeId }

        // Log the result of the recipe search
        if (recipe == null) {
            Log.e("RecipeDetailScreen", "Recipe not found with ID: $recipeId")
        } else {
            Log.d("RecipeDetailScreen", "Recipe found: ${recipe.title}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recipe Detail Screen Placeholder",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}