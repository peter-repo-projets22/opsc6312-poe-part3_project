package com.example.feedmytummy.navFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SwitchCompat
import com.example.feedmytummy.R


class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val themeSwitch = view.findViewById<SwitchCompat>(R.id.themeSwitch)
        val prefs =
            requireContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        themeSwitch.isChecked = isDarkMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
        }

        val backArrow = view.findViewById<ImageView?>(R.id.backArrow)
        backArrow?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, GroceryFragment())
                .addToBackStack(null)
                .commit()
        }

        val updateProfileButton = view.findViewById<View>(R.id.updateprofilebutton)
        updateProfileButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }
        val Languagebutton = view.findViewById<View>(R.id.Languagebutton)
        Languagebutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, LanguageFragment())
                .addToBackStack(null)
                .commit()
        }
        val Helpandsupportbutton = view.findViewById<View>(R.id.helpandsupportbutton)
        Helpandsupportbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HelpandSupportFragment())
                .addToBackStack(null)
                .commit()
        }
        val profileIcon = view.findViewById<ImageView?>(R.id.profileIcon)
        profileIcon?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}