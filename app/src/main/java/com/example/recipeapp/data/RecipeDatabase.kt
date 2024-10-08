package com.example.recipeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Database(entities = [RecipeEntity::class], version = 3, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Prepopulate the database
                            CoroutineScope(Dispatchers.IO).launch {
                                prepopulateRecipes(getDatabase(context).recipeDao())
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Prepopulate with some sample recipes
        private suspend fun prepopulateRecipes(recipeDao: RecipeDao) {
            val recipes = listOf(
                RecipeEntity(
                    title = "Paleo Chicken Stir Fry",
                    description = "A healthy and quick stir fry using paleo-friendly ingredients.",
                    ingredients = "Chicken, Broccoli, Garlic, Olive Oil",
                    instructions = "1. Heat the pan...\n2. Cook the chicken...",
                    mealType = "Dinner",
                    isFavorite = false,
                    imageUrl = "paleo_chicken_stir_fry"
                ),
                RecipeEntity(
                    title = "Grilled Chicken Salad with Avocado",
                    description = "A refreshing salad with grilled chicken and creamy avocado.",
                    ingredients = "Chicken Breast, Avocado, Mixed Greens, Olive Oil",
                    instructions = "1. Season chicken breast and cook...\n2. Slice the grilled chicken...",
                    mealType = "Lunch",
                    isFavorite = false,
                    imageUrl = "grilled_chicken_salad_with_avocado"
                ),
                RecipeEntity(
                    title = "Coconut Chia Pudding",
                    description = "A creamy, nutrient-rich pudding made with coconut milk and chia seeds.",
                    ingredients = "Eggs, Butter, Salt",
                    instructions = "1. In a bowl, mix chia seeds...\n2.  Stir well and...",
                    mealType = "Breakfast",
                    isFavorite = false,
                    imageUrl = "coconut_chia_pudding"
                )


                // Add more sample recipes
            )
            recipeDao.insertAll(recipes)
            Log.d("RecipeDatabase", "Inserted ${recipes.size} recipes")
        }
    }
}


