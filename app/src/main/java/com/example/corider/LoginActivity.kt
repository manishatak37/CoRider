package com.example.corider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.corider.databinding.ActivityLoginBinding
import com.example.corider.databinding.ActivityStartBinding
import com.example.corider.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference


    private val binding: ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        database = Firebase.database.reference




        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {

            //getting login data from user
            email =binding.loginemail.text.toString().trim()
            password= binding.loginpassword.text.toString().trim()

            if(email.isBlank()||password.isBlank()){

                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            }
            else{

                createUserAccount(email,password)

            }


        }
    }

    private fun createUserAccount(email: String, password: String) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->

            if(task.isSuccessful){
                val user :FirebaseUser? =auth.currentUser
                saveUserData()
                updateUI(user)


            }
            else{
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createUserAccount: failed", task.exception)
            }
        }

    }

    private fun saveUserData() {
        email =binding.loginemail.text.toString().trim()
        password= binding.loginpassword.text.toString().trim()

        val user=UserModel(email,password)
        val userId:String? =FirebaseAuth.getInstance().currentUser?.uid
        userId.let{


            if (it != null) {
                database.child("user").child(it).setValue(user)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        startActivity(Intent(this,MainActivity::class.java))


    }
}