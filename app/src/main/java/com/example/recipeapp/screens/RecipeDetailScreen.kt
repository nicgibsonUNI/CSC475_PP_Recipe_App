package com.example.recipeapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.components.BottomNavigationBar
import com.example.recipeapp.utils.getDrawableId
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

    // Find the recipe by ID outside of LaunchedEffect
    val recipe = recipes.find { it.id == recipeId }

    // If the recipe is null, display an error message
    if (recipe == null) {
        Text(text = "Error: Recipe not found")
        return
    }

    // Logging inside LaunchedEffect just for side effects like logging
    LaunchedEffect(recipeId) {
        Log.d("RecipeDetailScreen", "Recipe found: ${recipe.title}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details", style = MaterialTheme.typography.titleMedium) },
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
            // Recipe Image
            Image(
                painter = painterResource(id = getDrawableId(recipe.imageUrl, LocalContext.current)),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Recipe Title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Recipe Description
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Meal Type
            Text(
                text = "Meal Type: ${recipe.mealType}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Recipe Ingredients
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = recipe.ingredients,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Recipe Instructions
            Text(
                text = "Instructions:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = recipe.instructions,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


