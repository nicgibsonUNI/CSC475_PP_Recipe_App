package com.example.recipeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.utils.getDrawableId
import com.example.recipeapp.viewmodel.RecipeViewModel

@Composable
fun RecipeAppHomeScreen(navController: NavController, recipeViewModel: RecipeViewModel) {
    // Get the current context
    val context = LocalContext.current

    // Observe the random recipe from the ViewModel
    val randomRecipe by recipeViewModel.getRandomRecipe().observeAsState()

    Scaffold(
        content = { paddingValues ->  // Padding values are provided by Scaffold
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Apply scaffold padding
                    .padding(16.dp),  // Additional padding for inner content
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Search Bar for recipes
                var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

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
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                // Meal Type Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigate("searchResults/Breakfast") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Breakfast")
                    }
                    Button(
                        onClick = { navController.navigate("searchResults/Lunch") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Lunch")
                    }
                    Button(
                        onClick = { navController.navigate("searchResults/Dinner") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Dinner")
                    }
                }

                // Display random featured recipe
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (randomRecipe != null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // "Featured Recipe" title
                            Text(
                                text = "Featured Recipe",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Card to display Recipe details
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                                    .clickable {
                                        // Navigate to the recipe detail screen
                                        navController.navigate("recipeDetail/${randomRecipe!!.id}")
                                    },
                                elevation = CardDefaults.elevatedCardElevation(4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Recipe Image
                                    Image(
                                        painter = painterResource(
                                            id = getDrawableId(randomRecipe!!.imageUrl, context)
                                        ),
                                        contentDescription = randomRecipe!!.title,
                                        modifier = Modifier
                                            .size(400.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )

                                    // Recipe Title
                                    Text(
                                        text = randomRecipe!!.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )

                                    // Recipe Description
                                    Text(
                                        text = randomRecipe!!.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        Text("Loading featured recipe...", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    )
}

