package com.example.recipeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [RecipeEntity::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        // Pass applicationScope as a parameter to the database builder
        fun getDatabase(context: Context, applicationScope: CoroutineScope): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val newInstance: RecipeDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("RecipeDatabase", "Database created, starting recipe insertion.")
                            INSTANCE?.let { database ->
                                applicationScope.launch {
                                    val recipes = prepopulateRecipes()
                                    database.recipeDao().insertAll(recipes)

                                    // Log each recipe as it's inserted
                                    recipes.forEach {
                                        Log.d("RecipeDatabase", "Inserted recipe: ${it.title} with ID: ${it.id}")
                                    }
                                    Log.d("RecipeDatabase", "Prepopulation complete: ${recipes.size} recipes inserted")
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = newInstance
                newInstance
            }
        }




        // Prepopulate function to provide curated recipes
        fun prepopulateRecipes(): List<RecipeEntity> {
            return listOf(
                RecipeEntity(
                    id = 1, //Unique ID
                    title = "Paleo Chicken Stir Fry",
                    description = "A healthy and quick stir fry using paleo-friendly ingredients.",
                    ingredients = "Chicken, Broccoli, Garlic, Olive Oil",
                    instructions = "1. Heat the pan...\n2. Cook the chicken...",
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 2, //Unique ID
                    title = "Keto Beef Tacos",
                    description = "Delicious beef tacos wrapped in lettuce leaves.",
                    ingredients = "Ground Beef, Lettuce, Onion, Spices",
                    instructions = "1. Cook the beef...\n2. Prepare the lettuce wraps...",
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 3, //Unique ID
                    title = "Grilled Salmon with Avocado Salsa",
                    description = "A refreshing grilled salmon dish with a tasty avocado salsa.",
                    ingredients = "Salmon Fillets, Olive Oil, Lime Juice, Avocado, Tomato, Cilantro, Salt, Pepper",
                    instructions = """
                         1. Preheat grill to medium-high heat.
                         2. Drizzle salmon with olive oil and lime juice, and season with salt and pepper.
                         3. Grill salmon for 6-8 minutes per side, until cooked through.
                         4. In a bowl, combine diced avocado, tomato, cilantro, and lime juice to make salsa.
                         5. Serve grilled salmon topped with avocado salsa.
                    """.trimIndent(),
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 4, //Unique ID
                    title = "Sweet Potato Hash with Eggs",
                    description = "A hearty sweet potato hash with sunny-side-up eggs on top.",
                    ingredients = "Sweet Potatoes, Bell Peppers, Onions, Eggs, Olive Oil, Salt, Pepper",
                    instructions = """
                        1. Heat olive oil in a large pan over medium heat.
                        2. Add diced sweet potatoes, bell peppers, and onions. Cook until soft, about 10-12 minutes.
                        3. In a separate pan, fry eggs sunny-side-up.
                        4. Season the sweet potato hash with salt and pepper, then top with fried eggs.
                        5. Serve hot.
                    """.trimIndent(),
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 5, //Unique ID
                    title = "Cauliflower Rice with Garlic and Herbs",
                    description = "A light and fluffy cauliflower rice seasoned with garlic and fresh herbs.",
                    ingredients = "Cauliflower, Garlic, Olive Oil, Parsley, Salt, Pepper",
                    instructions = """
                        1. Grate or pulse cauliflower in a food processor until it resembles rice.
                        2. Heat olive oil in a pan over medium heat and sauté garlic for 1-2 minutes.
                        3. Add cauliflower rice and cook for 5-7 minutes, stirring occasionally.
                        4. Stir in freshly chopped parsley, salt, and pepper.
                        5. Serve as a side or base for other dishes.
                    """.trimIndent(),
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 6, //Unique ID
                    title = "Zucchini Noodles with Pesto",
                    description = "Light zucchini noodles tossed with a fresh basil pesto.",
                    ingredients = "Zucchini, Basil, Pine Nuts, Garlic, Olive Oil, Lemon Juice, Salt",
                    instructions = """
                        1. Spiralize the zucchini into noodles and set aside.
                        2. In a blender, combine basil, pine nuts, garlic, olive oil, and lemon juice to make pesto.
                        3. Heat the zucchini noodles in a pan for 2-3 minutes until slightly tender.
                        4. Toss with pesto and serve immediately.
                    """.trimIndent(),
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 7, //Unique ID
                    title = "Paleo Meatballs with Marinara",
                    description = "Tender meatballs served with a rich tomato marinara sauce.",
                    ingredients = "Ground Beef, Almond Flour, Egg, Garlic, Italian Seasoning, Canned Tomatoes, Olive Oil",
                    instructions = """
                        1. In a bowl, mix ground beef, almond flour, egg, garlic, and Italian seasoning.
                        2. Form meat mixture into small meatballs and set aside.
                        3. Heat olive oil in a pan and brown the meatballs on all sides.
                        4. In a separate pot, simmer canned tomatoes with olive oil and garlic for 20 minutes.
                        5. Add the meatballs to the marinara sauce and cook for an additional 10 minutes.
                        6. Serve hot.
                    """.trimIndent(),
                    isFavorite = false
                ),
                RecipeEntity(
                    id = 8, //Unique ID
                    title = "Roasted Brussels Sprouts with Bacon",
                    description = "Crispy roasted Brussels sprouts with savory bacon bits.",
                    ingredients = "Brussels Sprouts, Bacon, Olive Oil, Salt, Pepper",
                    instructions = """
                        1. Preheat oven to 400°F (200°C).
                        2. Toss halved Brussels sprouts with olive oil, salt, and pepper.
                        3. Spread on a baking sheet and roast for 20-25 minutes, until crispy.
                        4. In a pan, cook bacon until crispy, then crumble over the roasted Brussels sprouts.
                        5. Serve as a side dish.
                    """.trimIndent(),
                    isFavorite = false
                )
            )
        }
    }
}
