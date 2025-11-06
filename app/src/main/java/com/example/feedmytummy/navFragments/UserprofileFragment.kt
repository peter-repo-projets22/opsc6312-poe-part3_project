package com.example.feedmytummy.navFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.feedmytummy.DatabaseHelper
import com.example.feedmytummy.R
import com.example.feedmytummy.navFragments.AddFragment

class UserprofileFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var saveButton: Button
    private lateinit var backArrow: ImageView
    private lateinit var db: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_userprofile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        username = view.findViewById(R.id.etusername)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        saveButton = view.findViewById(R.id.button)
        backArrow = view.findViewById(R.id.backArrow)

        db = DatabaseHelper(requireContext())

        // Handle back navigation
        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .commit()
        }

        // Handle save profile button
        saveButton.setOnClickListener {
            val user = username.text.toString().trim()
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()

            if (user.isEmpty() || mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inserted = db.insertUser(user, mail, pass)
            if (inserted) {
                Toast.makeText(requireContext(), "backup crdentials stored!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error  storing backup credentails", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
