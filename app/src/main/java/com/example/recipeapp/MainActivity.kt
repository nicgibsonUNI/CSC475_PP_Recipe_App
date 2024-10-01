package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.viewmodel.RecipeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                // Home Screen
                composable("home") {
                    RecipeAppHomeScreen(navController)
                }

                // Recipe Detail Screen
                composable(
                    route = "recipeDetail/{recipeId}",
                    arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt("recipeId")
                    val recipeViewModel: RecipeViewModel = viewModel()
                    RecipeDetailScreen(recipeId, recipeViewModel, navController)
                }

                // Search Screen
                composable("search") {
                    SearchScreen(navController)
                    // Placeholder for search screen
                }

                // Favorite Screen
                composable("favorites") {
                    val recipeViewModel: RecipeViewModel = viewModel()  // Get the ViewModel instance
                    FavoritesScreen(recipeViewModel, navController)
                }

                // Profile Screen
                composable("profile") {
                    ProfileScreen(navController)
                }
            }
        }
    }
}

@Composable
fun RecipeAppHomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }  // Include bottom navigation
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ancestral Recipe and Cooking App",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { newQuery: TextFieldValue -> searchQuery = newQuery },
                placeholder = { Text(text = "Search for recipes...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = MaterialTheme.shapes.medium,  // This will give rounded corners
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Meal Type Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /* Handle Breakfast */ }) {
                    Text("Breakfast")
                }
                Button(onClick = { /* Handle Lunch */ }) {
                    Text("Lunch")
                }
                Button(onClick = { /* Handle Dinner */ }) {
                    Text("Dinner")
                }
            }

            // Placeholder for Featured Recipe
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Featured Recipe Placeholder")
            }

            // Recipe List Placeholder
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                repeat(3) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                Log.d("HomeScreen", "Navigating to recipeDetail with ID: ${index + 1}")
                                navController.navigate("recipeDetail/${index + 1}")
                            },
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Text(
                            text = "Recipe ${index + 1}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(recipeId: Int?, recipeViewModel: RecipeViewModel, navController: NavController) {
    // Log the recipeId being passed
    Log.d("RecipeDetailScreen", "Received recipeId: $recipeId")

    if (recipeId == null) {
        Log.e("RecipeDetailScreen", "Recipe ID is null")
        Text(text = "Invalid recipe ID", modifier = Modifier.padding(16.dp))
        return
    }

    // Observe recipes from the ViewModel
    val recipes by recipeViewModel.allRecipes.observeAsState(emptyList())

    // Log the size of the recipes list
    Log.d("RecipeDetailScreen", "Total recipes available: ${recipes.size}")

    // Trigger recipe search and log when recipes are loaded
    LaunchedEffect(recipes) {
        val recipe = recipes.find { it.id == recipeId }

        // Log the result of the recipe search
        if (recipe == null) {
            Log.e("RecipeDetailScreen", "Recipe not found with ID: $recipeId")
        } else {
            Log.d("RecipeDetailScreen", "Recipe found: ${recipe.title}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recipe Detail Screen Placeholder",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }  // Search query state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Recipes") }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }  // Include the bottom navigation bar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { newQuery: TextFieldValue -> searchQuery = newQuery },
                placeholder = { Text(text = "Search recipes...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,  // Background color when focused
                    unfocusedContainerColor = Color.LightGray,  // Background color when not focused
                    focusedIndicatorColor = Color.Transparent,  // Remove underline when focused
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Show search results or placeholder
            if (searchQuery.text.isNotEmpty()) {
                // Add logic here to display search results based on the query
                Text("Results for: ${searchQuery.text}")
            } else {
                Text(
                    text = "Start typing to search for recipes...",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(recipeViewModel: RecipeViewModel, navController: NavController) {
    val favoriteRecipes by recipeViewModel.favoriteRecipes.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (favoriteRecipes.isEmpty()) {
                Text(
                    text = "No favorite recipes yet.",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(favoriteRecipes) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("recipeDetail/${recipe.id}")
                                },
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = recipe.title, fontSize = 18.sp, color = Color.Black)
                                Text(text = recipe.description, fontSize = 14.sp, color = Color.Gray)

                                // Button to toggle favorite status
                                IconButton(
                                    onClick = {
                                        val updatedRecipe = recipe.copy(isFavorite = false)  // Unmark as favorite
                                        recipeViewModel.updateRecipe(updatedRecipe)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Remove from Favorites",
                                        tint = Color.Yellow
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }  // Bottom navigation bar included
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // User Information Section
            Text(
                text = "Welcome, User",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Profile Picture Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                    .background(Color.Gray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "User",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Favorite Management Section (e.g., clear favorites)
            Button(
                onClick = {
                    // Handle clear favorites logic here (e.g., call viewModel to reset favorites)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Favorites")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Future Features: Settings or Notifications
            Button(
                onClick = {
                    // Handle future preferences or settings logic
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("App Settings (Coming Soon)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logout or Exit (For future versions if you implement user authentication)
            Button(
                onClick = {
                    // Handle logout or exit functionality
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Logout")
            }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(60.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly  // Space icons evenly
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { navController.navigate("search") }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { navController.navigate("favorites") }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorites",
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}




