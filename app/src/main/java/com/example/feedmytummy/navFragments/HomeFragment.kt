package com.example.feedmytummy.navFragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.feedmytummy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val welcomeText = view.findViewById<TextView>(R.id.textView6)
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        if (userId != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
            dbRef.child("name").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(String::class.java)
                    if (!name.isNullOrBlank()) {
                        welcomeText.text = "Welcome, $name"
                    } else {
                        val email = user.email ?: "User"
                        welcomeText.text = "Welcome, $email"
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    val email = user.email ?: "User"
                    welcomeText.text = "Welcome, $email"
                }
            })
        }
        val profileIcon = view.findViewById<View>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}