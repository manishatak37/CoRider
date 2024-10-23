package com.example.corider // Replace with your actual package name

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PublishSucess : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_sucess)

        // Initialize the continue button
        val continueButton: Button = findViewById(R.id.continue_button)

        // Retrieve session values from SharedPreferences
        val sharedPreferences = getSharedPreferences("RideDetails", MODE_PRIVATE)

        // Get all keys and their corresponding values
        val allEntries = sharedPreferences.all

        // Print each entry in Logcat
        for ((key, value) in allEntries) {
            Log.d("SessionValue", "$key: $value")
        }

        continueButton.setOnClickListener {
            // Redirect to the main activity or any other page
            val intent = Intent(this, MainActivity::class.java) // Replace with your main activity
            startActivity(intent)
            finish() // Close the success page
        }
    }
}
