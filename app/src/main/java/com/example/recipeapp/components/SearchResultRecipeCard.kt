package com.example.recipeapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp.data.RecipeEntity

@Composable
fun SearchResultRecipeCard(recipe: RecipeEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface) // Use surface color from the theme
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Image component commented out, possibly put back later
            /*
            Image(
                painter = rememberAsyncImagePainter(model = recipe.imageUrl),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)), // Ensure rounded corners for the image as well
                contentScale = ContentScale.Crop
            )
            */

            // Recipe title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary, // Ensure correct text color
                modifier = Modifier.padding(top = 8.dp)
            )

            // Recipe description
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary, // Ensure correct text color
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}



