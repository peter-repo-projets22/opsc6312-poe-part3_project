package com.example.feedmytummy.navFragments

import androidx.fragment.app.Fragment
import com.example.feedmytummy.R


class ScaleRecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_scale_recipe, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backArrow = view.findViewById<android.widget.ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .commit()
        }
        val profilebutton = view.findViewById<android.widget.ImageView>(R.id.profileIcon)
        profilebutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .commit()
        }
    }
}