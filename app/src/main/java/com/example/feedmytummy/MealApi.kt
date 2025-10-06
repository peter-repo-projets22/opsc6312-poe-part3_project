package com.example.feedmytummy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


// DEFINES THE API FUNCTIONS eg, listing meals by category , used by Retrofit to generate the actual network code
interface MealApi {

    // Fetch meals by category
    @GET("filter.php")
    fun getMealsByCategory(
            @Query("c") category: String
    ): Call<MealResponse>

    // Search meals by name
    @GET("search.php")
    fun searchMeals(
            @Query("s") name: String
    ): Call<MealResponse>

    // Lookup meal details by ID
    @GET("lookup.php")
    fun getMealById(
            @Query("i") id: String
    ): Call<MealResponse>


}
