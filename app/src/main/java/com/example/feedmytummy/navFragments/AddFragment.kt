package com.example.feedmytummy.navFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.feedmytummy.R
import com.example.feedmytummy.databinding.FragmentAddBinding

class AddFragment : Fragment() {


    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createrecipebutton.setOnClickListener {
            // Simple fragment replacement
            val newFragment = CreateFragment() // Your target fragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }
         binding.overviewButton.setOnClickListener {
            // Simple fragment replacement
            val newFragment = RecipeOverviewFragment() // Your target fragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }
        binding.deleteButton.setOnClickListener {
            // Simple fragment replacement
            val newFragment = DeleteFragment() // Your target fragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }

        binding.editButton.setOnClickListener {
            // Simple fragment replacement
            val newFragment = EditFragment() // Your target fragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }

        binding.scaleRecipeButton.setOnClickListener {
            // Simple fragment replacement
            val newFragment = ScaleRecipeFragment() // Your target fragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }

        binding.backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, SearchFragment())
                .commit()
        }

        binding.viewRecipeButton.setOnClickListener() {
            // Simple fragment replacement
            val newFragment = RecipeViewFragment() // Your target fragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }

        // Use view binding for profile icon if possible
        val profileIcon = binding.profileIcon
        profileIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}