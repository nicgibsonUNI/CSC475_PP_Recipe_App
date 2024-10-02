package com.example.recipeapp.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("DiscouragedApi")
fun getDrawableId(imageName: String, context: Context): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}   // Static resource isn't feasible right now for the feature wanted.

