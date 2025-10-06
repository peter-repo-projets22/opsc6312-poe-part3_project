package com.example.feedmytummy.navFragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import androidx.fragment.app.Fragment
import com.example.feedmytummy.Ingredients
import com.example.feedmytummy.R

class Recipe(
    val name: String = "",
    val type: String = "",
    val ingredientName: String = "",
    val quantity: String = "",
    val quantityy: Double = 0.0,
    var ingredients: List<Ingredients> = emptyList(),
    val unit: String = "",
    val category: String = "",
    val steps: String = "",
    val prepTime: String = "",
    val cookingTime: String = "",
    val imageUrl: String = "",
    val description: String = ""
)

class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backArrow = view.findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .commit()
        }
        val etRecipeName = view.findViewById<TextInputEditText>(R.id.etRecipeName)
        val etRecipeType = view.findViewById<TextInputEditText>(R.id.etRecipeType)
        val etIngredientName = view.findViewById<TextInputEditText>(R.id.etIngredientName)
        val etQuantity = view.findViewById<TextInputEditText>(R.id.etQuantity)
        val etUnit = view.findViewById<TextInputEditText>(R.id.etUnit)
        val spinnerCategory = view.findViewById<android.widget.AutoCompleteTextView>(R.id.spinnerCategory)
        // Set category from arguments if available
        val selectedCategory = arguments?.getString("category")
        if (!selectedCategory.isNullOrEmpty()) {
            spinnerCategory.setText(selectedCategory, false)
        }
        val etSteps = view.findViewById<TextInputEditText>(R.id.etSteps)
        val etPrepTime = view.findViewById<TextInputEditText>(R.id.etPrepTime)
        val etCookTime = view.findViewById<TextInputEditText>(R.id.etCookTime)
        val etDescription = view.findViewById<TextInputEditText>(R.id.etDescription)
        val btnStoreRecipe = view.findViewById<MaterialButton>(R.id.btnStoreRecipe)
        btnStoreRecipe.setOnClickListener {
            val recipe = Recipe(
                name = etRecipeName.text?.toString() ?: "",
                type = etRecipeType.text?.toString() ?: "",
                ingredientName = etIngredientName.text?.toString() ?: "",
                quantity = etQuantity.text?.toString() ?: "",
                unit = etUnit.text?.toString() ?: "",
                category = spinnerCategory.text?.toString() ?: "",
                steps = etSteps.text?.toString() ?: "",
                prepTime = etPrepTime.text?.toString() ?: "",
                cookingTime = etCookTime.text?.toString() ?: "",
                imageUrl = "",
                description = etDescription.text?.toString() ?: ""
            )
            val dbRef = FirebaseDatabase.getInstance().getReference("recipes")
            val recipeId = dbRef.push().key
            if (recipeId != null) {
                dbRef.child(recipeId).setValue(recipe)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Recipe saved!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to save recipe.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
