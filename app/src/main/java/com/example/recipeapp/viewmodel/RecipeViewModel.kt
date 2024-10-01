package com.example.recipeapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeDatabase
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.RecipeEntity
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository
    val allRecipes: LiveData<List<RecipeEntity>>
    val favoriteRecipes: LiveData<List<RecipeEntity>>

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)

        allRecipes = repository.allRecipes.asLiveData()
        favoriteRecipes = repository.favoriteRecipes.asLiveData()
    }

    fun insertRecipe(recipe: RecipeEntity) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun updateRecipe(recipe: RecipeEntity) = viewModelScope.launch {
        repository.updateRecipe(recipe)
    }

    fun getRecipeById(recipeId: Int): LiveData<RecipeEntity?> {
        return repository.getRecipeById(recipeId).asLiveData()
    }
}

