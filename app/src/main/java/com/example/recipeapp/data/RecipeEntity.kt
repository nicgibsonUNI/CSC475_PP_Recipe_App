package com.example.recipeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val description: String,
    val ingredients: String,
    val instructions: String,
    val mealType: String,
    val isFavorite: Boolean = false,
    val imageUrl: String
)


