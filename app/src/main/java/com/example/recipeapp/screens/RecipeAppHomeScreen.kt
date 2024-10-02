package com.example.recipeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipeapp.components.BottomNavigationBar
import com.example.recipeapp.utils.getDrawableId
import com.example.recipeapp.viewmodel.RecipeViewModel

@Composable
fun RecipeAppHomeScreen(navController: NavController, recipeViewModel: RecipeViewModel) {
    // Get the current context
    val context = LocalContext.current

    // Observe the random recipe from the ViewModel
    val randomRecipe by recipeViewModel.getRandomRecipe().observeAsState()

    Scaffold(
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
                text = "Ancestral Recipe and Cooking App",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

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
                Button(onClick = {
                    navController.navigate("searchResults/Breakfast")
                }) {
                    Text("Breakfast")
                }
                Button(onClick = {
                    navController.navigate("searchResults/Lunch")
                }) {
                    Text("Lunch")
                }
                Button(onClick = {
                    navController.navigate("searchResults/Dinner")
                }) {
                    Text("Dinner")
                }
            }

            // Display random featured recipe
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //  .height(300.dp)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (randomRecipe != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // "Featured Recipe" title above  recipe
                        Text(
                            text = "Featured Recipe",
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)  // Add some spacing between the text and the card
                        )

                        // Card to display Recipe details
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                            elevation = CardDefaults.elevatedCardElevation(4.dp), // Fix for the elevation
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Recipe Image
                                Image(
                                    painter = painterResource(
                                        id = getDrawableId(
                                            randomRecipe!!.imageUrl,
                                            context
                                        )
                                    ),
                                    contentDescription = randomRecipe!!.title,
                                    modifier = Modifier
                                        .size(400.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                // Recipe Title
                                Text(
                                    text = randomRecipe!!.title,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 8.dp)
                                )

                                // Recipe Description
                                Text(
                                    text = randomRecipe!!.description,
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                } else {
                    Text("Loading featured recipe...")
                }
            }
        }
    }
}
