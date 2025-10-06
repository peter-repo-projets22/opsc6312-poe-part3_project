package com.example.feedmytummy.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.feedmytummy.navFragments.AddFragment
import com.example.feedmytummy.navFragments.CreateFragment
import com.example.feedmytummy.navFragments.HomeFragment
import com.example.feedmytummy.navFragments.SearchFragment
import com.example.feedmytummy.R
import com.example.feedmytummy.navFragments.GroceryFragment
import com.example.feedmytummy.navFragments.SettingsFragment
import com.example.feedmytummy.signinscreens.SignInFragment
import com.example.feedmytummy.signinscreens.WelcomeFragment
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.button_home -> replaceFragment(HomeFragment())
                R.id.button_search -> replaceFragment(SearchFragment())
                R.id.button_add -> replaceFragment(AddFragment())
                R.id.button_groceries -> replaceFragment(GroceryFragment())
                R.id.button_settings -> replaceFragment(SettingsFragment())
                else -> false
            }
            true
        }

        // Default fragment
        if (savedInstanceState == null) {
            replaceFragment(WelcomeFragment())
        }
    }

    fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
        // Hide bottom nav only for authentication fragments
        val hideNavFragments = listOf(
            com.example.feedmytummy.signinscreens.WelcomeFragment::class,
            com.example.feedmytummy.signinscreens.LoginFragment::class,
            com.example.feedmytummy.signinscreens.SignupFragment::class,
            com.example.feedmytummy.signinscreens.SignInFragment::class
        )
        if (hideNavFragments.contains(fragment::class)) {
            bottomNavigationView.visibility = android.view.View.GONE
        } else {
            bottomNavigationView.visibility = android.view.View.VISIBLE
        }
        return true
    }
}
