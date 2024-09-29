package com.example.recipeapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipeApp
import com.example.recipeapp.data.RecipeEntity
import com.example.recipeapp.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    // Repository to handle database operations
    private val repository: RecipeRepository

    // Convert Flow to LiveData
    val allRecipes: LiveData<List<RecipeEntity>>
    val favoriteRecipes: LiveData<List<RecipeEntity>>

    init {
        // Get the database from the RecipeApp class and create the repository
        val recipeDao = (application as RecipeApp).database.recipeDao()
        repository = RecipeRepository(recipeDao)

        // Convert Flow to LiveData here
        allRecipes = repository.allRecipes.asLiveData()
        favoriteRecipes = repository.favoriteRecipes.asLiveData()

        Log.d("RecipeViewModel", "Initialized with ${allRecipes.value?.size ?: 0} recipes.")
    }

    // Function to mark a recipe as favorite
    fun toggleFavorite(recipe: RecipeEntity) = viewModelScope.launch {
        recipe.isFavorite = !recipe.isFavorite
        Log.d("RecipeViewModel", "Toggling favorite for recipe ID: ${recipe.id}, New status: ${recipe.isFavorite}")
        repository.updateRecipe(recipe)
    }

    // Function to insert new recipes
    fun insertRecipe(recipe: RecipeEntity) = viewModelScope.launch {
        Log.d("RecipeViewModel", "Inserting new recipe: ${recipe.title}")
        repository.insertRecipe(recipe)
    }

    // Function to update a recipe (for Recipe Detail Screen)
    fun updateRecipe(recipe: RecipeEntity) = viewModelScope.launch {
        Log.d("RecipeViewModel", "Updating recipe ID: ${recipe.id}")
        repository.updateRecipe(recipe)
    }

    // Function to get a recipe by ID (for Recipe Detail Screen)
    fun getRecipeById(recipeId: Int): LiveData<RecipeEntity?> {
        Log.d("RecipeViewModel", "Fetching recipe by ID: $recipeId")
        return repository.getRecipeById(recipeId).asLiveData()  // Convert Flow to LiveData
    }
}

