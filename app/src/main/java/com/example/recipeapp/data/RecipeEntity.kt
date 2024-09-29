package com.example.recipeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")  // Define the table name
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)  // Auto-generate IDs for the table
    val id: Int = 0,                  // Recipe ID
    val title: String,                // Recipe title
    val description: String,          // Recipe description
    val ingredients: String,          // Recipe ingredients
    val instructions: String,         // Recipe instructions
    var isFavorite: Boolean = false   // Whether the recipe is marked as favorite
)
