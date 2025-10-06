package com.example.feedmytummy.navFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.feedmytummy.R
import com.google.firebase.database.*

class RecipeViewFragment : Fragment() {

    private lateinit var ingredientsLayout: LinearLayout
    private lateinit var servingsInput: EditText
    private lateinit var scaleButton: Button
    private var currentRecipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backArrow = view.findViewById<ImageView>(R.id.backArrow)
        val titleView = view.findViewById<TextView>(R.id.textView10)
        val descView = view.findViewById<TextView>(R.id.textView8)
        ingredientsLayout = view.findViewById(R.id.linearLayout)
        servingsInput = view.findViewById(R.id.etServings)
        scaleButton = view.findViewById(R.id.btnScaleRecipe)

        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .commit()
        }

        // Fetch latest recipe
        val dbRef = FirebaseDatabase.getInstance().getReference("recipes")

        dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val recipe = child.getValue(Recipe::class.java)
                    if (recipe != null) {
                        currentRecipe = recipe

                        // Update top section
                        titleView.text = recipe.name
                        descView.text = "${recipe.description}\nPrep duration : ${recipe.prepTime}  •  Cook duration: ${recipe.cookingTime}"

//                        // Load recipe image if available
//                        val imageView = view.findViewById<ImageView>(R.id.imageView5)
//                        if (!recipe.imageUrl.isNullOrEmpty()) {
//                            // You can use Glide or Picasso to load images from URLs
//                            Glide.with(requireContext())
//                                .load(recipe.imageUrl)
//                                .placeholder(R.drawable.placeholder_image)
//                                .into(imageView)
//                        }

                        // Display all ingredients
                        displayIngredients(recipe)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


        // Handle scaling
        scaleButton.setOnClickListener {
            val multiplier = servingsInput.text.toString().toDoubleOrNull()
            val recipe = currentRecipe

            if (recipe != null && multiplier != null && multiplier > 0) {
                displayIngredients(recipe, multiplier)
            } else {
                Toast.makeText(requireContext(), "Enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayIngredients(recipe: Recipe, multiplier: Double = 1.0) {
        ingredientsLayout.removeAllViews()

        if (recipe.ingredients.isNotEmpty()) {
            for ((index, ingredient) in recipe.ingredients.withIndex()) {
                val ingredientText = TextView(requireContext())

                // Scale quantity
                val scaledQuantity = ingredient.quantity * multiplier

                ingredientText.text = "${index + 1}. ${scaledQuantity} ${ingredient.unit} ${ingredient.name}"
                ingredientText.textSize = 14f
                ingredientText.setTextColor(resources.getColor(R.color.black, null))
                ingredientsLayout.addView(ingredientText)
            }
        } else {
            // Fallback if the list is empty
            val ingredientText = TextView(requireContext())
            ingredientText.text = "${recipe.quantityy} ${recipe.unit} ${recipe.ingredientName}"
            ingredientText.textSize = 14f
            ingredientText.setTextColor(resources.getColor(R.color.black, null))
            ingredientsLayout.addView(ingredientText)
        }
    }

}
