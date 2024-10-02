package com.example.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipeapp.components.BottomNavigationBar

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