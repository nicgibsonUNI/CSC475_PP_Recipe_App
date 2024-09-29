package com.example.recipeapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    // Insert individual recipes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: RecipeEntity)

    // Insert multiple recipes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Update
    suspend fun update(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe_table WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?>

    // Add the missing getFavoriteRecipes method here
    @Query("SELECT * FROM recipe_table WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>
}

