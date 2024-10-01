package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.data.RecipeDatabase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class RecipeApp : Application() {

    // Lazy initialization of the local Room database
    val database by lazy { RecipeDatabase.getDatabase(this) }

    // Firestore reference
    lateinit var firestore: FirebaseFirestore

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // After ensuring Firebase is initialized, initialize Firestore
        firestore = FirebaseFirestore.getInstance()
    }
}


