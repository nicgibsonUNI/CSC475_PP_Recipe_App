package com.example.recipeapp.data


data class RecipeFirestoreModel(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: String = "",
    val mealType: String = "",
    var isFavorite: Boolean = false
)

