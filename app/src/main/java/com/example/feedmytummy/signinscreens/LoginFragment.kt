package com.example.feedmytummy.signinscreens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.feedmytummy.R
import com.example.feedmytummy.navFragments.HomeFragment
import com.example.feedmytummy.activities.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.app.Activity.RESULT_OK

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // from google-services.json
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signUpButton = view.findViewById<View>(R.id.signup_button)
        val signInButton = view.findViewById<View>(R.id.signin_button)
        val googleButton = view.findViewById<ImageView>(R.id.googleSignInBtn) // Your ImageView button

        // Normal Sign-In & Sign-Up navigation
        signUpButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, SignupFragment())
                .addToBackStack(null)
                .commit()
        }

        signInButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, SignInFragment())
                .addToBackStack(null)
                .commit()
        }

        // Google Sign-In button click
        googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    // Handle Google Sign-In result
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Toast.makeText(requireContext(), "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginFragment", "Google sign-in error", e)
                }
            }
        }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    (requireActivity() as? MainActivity)?.replaceFragment(HomeFragment())
                } else {
                    Toast.makeText(requireContext(), "Firebase Auth failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
