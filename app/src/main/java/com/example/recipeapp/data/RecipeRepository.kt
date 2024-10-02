package com.example.recipeapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow


class RecipeRepository(private val recipeDao: RecipeDao) {

    // Function to get all recipes from Room
    val allRecipes: Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    // Function to get favorite recipes from Room
    val favoriteRecipes: Flow<List<RecipeEntity>> = recipeDao.getFavoriteRecipes()

    // Insert a new recipe
    suspend fun insertRecipe(recipe: RecipeEntity) {
        recipeDao.insert(recipe)
    }

    // Update an existing recipe
    suspend fun updateRecipe(recipe: RecipeEntity) {
        recipeDao.update(recipe)
    }

    // Get a specific recipe by ID
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?> {
        return recipeDao.getRecipeById(recipeId)
    }

    // Get recipes by the meal type: 'breakfast', 'lunch', 'dinner'
    fun getRecipesByMealType(mealType: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByMealType(mealType)
    }

    // Delete a recipe
    suspend fun deleteRecipe(recipe: RecipeEntity) {
        recipeDao.delete(recipe)
    }

    // Get random recipe for "Featured Recipe"
    fun getRandomRecipe(): Flow<RecipeEntity?> {
        return recipeDao.getRandomRecipe()
    }

}


