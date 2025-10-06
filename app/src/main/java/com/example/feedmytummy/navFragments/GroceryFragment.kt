package com.example.feedmytummy.navFragments

import androidx.fragment.app.Fragment
import com.example.feedmytummy.R


class GroceryFragment : Fragment() {
    private val groceryList = mutableListOf<String>()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_grocerie, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<android.widget.EditText>(R.id.editTextText7)
        val tvGroceryList = view.findViewById<android.widget.TextView>(R.id.tvGroceryList)
        val addButton = view.findViewById<android.widget.Button>(R.id.addButton)
        addButton.setOnClickListener {
            val item = editText.text.toString().trim()
            if (item.isNotEmpty()) {
                groceryList.add(item)
                editText.text.clear()
                tvGroceryList.text = groceryList.mapIndexed { i, s -> "${i + 1}. $s" }.joinToString("\n")
            }
        }
        val backArrow = view.findViewById<android.widget.ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .commit()
        }
        val profileIcon = view.findViewById<android.widget.ImageView>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}