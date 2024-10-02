package com.example.recipeapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


// Functions used to interact with Room database
@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE mealType = :mealType")
    fun getRecipesByMealType(mealType: String): Flow<List<RecipeEntity>>

    @Update
    suspend fun update(recipe: RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes ORDER BY RANDOM() LIMIT 1")
    fun getRandomRecipe(): Flow<RecipeEntity?>

}


