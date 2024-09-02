package com.example.corider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.corider.databinding.ActivitySignBinding
import com.example.corider.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var userPassword: String
    private lateinit var userName: String
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize Firebase Database
        database = Firebase.database.reference

        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.createButton.setOnClickListener {
            userName = binding.userName.text.toString().trim()
            email = binding.loginemail.text.toString().trim()
            userPassword = binding.password.text.toString().trim()

            if (userName.isBlank() || email.isBlank() || userPassword.isBlank()) {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, userPassword)
            }
        }
    }

    private fun createAccount(email: String, userPassword: String) {

        auth.createUserWithEmailAndPassword(email, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created Successfully!", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account creation Failed!", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }

    private fun saveUserData() {
        userName = binding.userName.text.toString().trim()
        email = binding.loginemail.text.toString().trim()
        userPassword = binding.password.text.toString().trim()
        val user=UserModel(userName,email,userPassword)
        val userId:String = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

    }
}
