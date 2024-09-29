package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.data.RecipeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RecipeApp : Application() {

    // Create a CoroutineScope tied to the application's lifecycle
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Lazy initialization of the database using the applicationScope
    val database by lazy { RecipeDatabase.getDatabase(this, applicationScope) }
}