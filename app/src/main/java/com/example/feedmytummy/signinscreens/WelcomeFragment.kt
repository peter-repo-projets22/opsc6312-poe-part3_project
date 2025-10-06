package com.example.feedmytummy.signinscreens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.example.feedmytummy.R


class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, LoginFragment())
                .commit()
        }, 2000) // 2 seconds delay
    }
}
