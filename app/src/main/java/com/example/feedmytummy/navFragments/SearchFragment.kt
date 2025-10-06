package com.example.feedmytummy.navFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedmytummy.R
import com.example.feedmytummy.adapters.RecipeAdapter
import com.google.firebase.database.*

class SearchFragment : Fragment() {
    private val recipesList = mutableListOf<Recipe>()
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backArrow = view.findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HomeFragment())
                .commit()
        }
        val searchEdit = view.findViewById<EditText>(R.id.searchRecipes)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recipesRecyclerView)
        adapter = RecipeAdapter(recipesList) { recipe ->
            // TODO: Handle click to show recipe details if needed
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRecipes(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val profileIcon = view.findViewById<ImageView>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }

        val fruitImage = view.findViewById<ImageView>(R.id.imageFruit)
        val veggieImage = view.findViewById<ImageView>(R.id.imageVeggie)
        val dairyImage = view.findViewById<ImageView>(R.id.imageDairy)
        val carbImage = view.findViewById<ImageView>(R.id.imageCarbs)

        fruitImage.setOnClickListener {
            openCreateFragmentWithCategory("Fruit")
        }
        veggieImage.setOnClickListener {
            openCreateFragmentWithCategory("Vegetable")
        }
        dairyImage.setOnClickListener {
            openCreateFragmentWithCategory("Dairy")
        }
        carbImage.setOnClickListener {
            openCreateFragmentWithCategory("Carbohydrate")
        }
    }

    private fun searchRecipes(query: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("recipes")
        dbRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    recipesList.clear()
                    for (child in snapshot.children) {
                        val recipe = child.getValue(Recipe::class.java)
                        if (recipe != null) recipesList.add(recipe)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun openCreateFragmentWithCategory(category: String) {
        val fragment = CreateFragment()
        val args = Bundle()
        args.putString("category", category)
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
