package com.example.recipeapp.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {

    // Get all recipes
    val allRecipes: Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    // Get all favorite recipes
    val favoriteRecipes: Flow<List<RecipeEntity>> = recipeDao.getFavoriteRecipes()

    // Function to insert a recipe (Room requires @WorkerThread for database operations)
    @WorkerThread
    suspend fun insertRecipe(recipe: RecipeEntity) {
        recipeDao.insert(recipe)
    }

    // Function to update a recipe (for toggling favorites)
    @WorkerThread
    suspend fun updateRecipe(recipe: RecipeEntity) {
        recipeDao.update(recipe)
    }

    // Function to get a recipe by ID
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?> {
        return recipeDao.getRecipeById(recipeId)
    }
}
