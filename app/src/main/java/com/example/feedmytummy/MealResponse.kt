package com.example.feedmytummy

// data class to map the JSON response from the API
// Lets Gson convert the API response into kotlin objects
data class MealResponse(
    val meals: List<Meal>
)

// data class representing one meal
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String
)