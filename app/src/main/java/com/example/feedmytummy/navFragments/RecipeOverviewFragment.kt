package com.example.feedmytummy.navFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedmytummy.ApiClient
import com.example.feedmytummy.MealAdapter
import com.example.feedmytummy.MealResponse
import com.example.feedmytummy.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeOverviewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_over_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backArrow = view.findViewById<ImageView>(R.id.backArrow)
        recyclerView = view.findViewById(R.id.mealRecyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load meals by default category
        fetchMealsByCategory("Seafood")

        val profileIcon = view.findViewById<ImageView>(R.id.profilebutton)
        profileIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }


        val backbutton = view.findViewById<ImageView>(R.id.backButton)
        backbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .addToBackStack(null)
                .commit()
        }
        // Handle search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchMeals(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchMealsByCategory(category: String) {
        ApiClient.mealApi.getMealsByCategory(category).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    adapter = MealAdapter(meals) // No click listener
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("MealAPI", "Error: ${t.localizedMessage}")
            }
        })
    }

    private fun searchMeals(query: String) {
        ApiClient.mealApi.searchMeals(query).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    adapter = MealAdapter(meals) // No click listener
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("SearchMeals", "Search failed: ${t.localizedMessage}")
            }
        })
    }
}
