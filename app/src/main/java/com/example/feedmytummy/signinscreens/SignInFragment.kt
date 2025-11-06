package com.example.feedmytummy.signinscreens

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.feedmytummy.navFragments.HomeFragment
import com.example.feedmytummy.R
import java.util.Locale

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    companion object {
        var selectedLanguage: String = "en"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val emailEditText = view.findViewById<EditText>(R.id.email)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val signInButton = view.findViewById<Button>(R.id.SignInbutton)
        val signupLinkButton = view.findViewById<Button>(R.id.signuplinkbutton)

        val englishButton = view.findViewById<Button>(R.id.englishbutton)
        val zuluButton = view.findViewById<Button>(R.id.Zulubutton)
        val afrikaansButton = view.findViewById<Button>(R.id.afrikaansbutton)

        // 🌍 Language switchers
        englishButton?.setOnClickListener { changeLanguage("en") }
        zuluButton?.setOnClickListener { changeLanguage("zu") }
        afrikaansButton?.setOnClickListener { changeLanguage("af") }

        // 🔐 Sign-in logic
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        (requireActivity() as? com.example.feedmytummy.activities.MainActivity)
                            ?.replaceFragment(HomeFragment())
                    } else {
                        Toast.makeText(
                            context,
                            "Login failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        signupLinkButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, SignupFragment())
                .commit()
        }
    }

    /** Apply locale and recreate activity */
    private fun changeLanguage(language: String) {
        selectedLanguage = language
        applyLanguage(requireContext(), language)
        requireActivity().recreate() // reloads UI with new language
    }

    /** Actually set locale on the app context */
    private fun applyLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
