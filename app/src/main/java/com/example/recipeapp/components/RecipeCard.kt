package com.example.recipeapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.recipeapp.data.RecipeEntity

@Composable
fun RecipeCard(recipe: RecipeEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Use surface color from theme
        ),
        shape = RoundedCornerShape(8.dp) // Adding rounded corners to align with the theme's natural look
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Recipe image
            Image(
                painter = rememberAsyncImagePainter(model = recipe.imageUrl),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)), // Clip the image to fit rounded corners
                contentScale = ContentScale.Crop // Ensure the image fits the card size
            )
            // Recipe title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface, // Use theme color for text
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            // Recipe description
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Slightly lighter text color
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}



