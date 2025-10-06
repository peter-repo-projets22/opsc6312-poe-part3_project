package com.example.feedmytummy

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// this set up retrofit - the http client that talks to TheMealDB API, USED CALL ENDPOINTS LIKE getMealsCategory("SEAFOOD")
object ApiClient {
    val mealApi: MealApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }
}