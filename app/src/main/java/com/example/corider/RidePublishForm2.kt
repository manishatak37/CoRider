package com.example.corider // Change this to your actual package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RidePublishForm2 : AppCompatActivity() {

    private lateinit var costInput: EditText
    private lateinit var publishButton: Button
    private var currentCost: String? = null // Temporary variable to store cost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_publish_form2) // Change to your actual layout name

        costInput = findViewById(R.id.cost_input)
        publishButton = findViewById(R.id.publish_button)

        publishButton.setOnClickListener {
            publishRide()
        }
    }

    private fun publishRide() {
        val cost = costInput.text.toString().trim() // Trim whitespace

        if (cost.isNotEmpty()) {
            currentCost = cost // Store cost in the temporary variable

            // Save the cost in SharedPreferences
            val sharedPreferences = getSharedPreferences("RideDetails", MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("cost", currentCost) // Store the cost
                apply() // Commit the changes
            }

            // Handle the cost submission logic here (e.g., saving to a database)
            Toast.makeText(this, "Ride cost of ₹$cost published!", Toast.LENGTH_SHORT).show()

            // Start the success activity
            val intent = Intent(this, PublishSucess::class.java) // Ensure PublishSuccess is your success activity
            startActivity(intent)

            // Clear the input after publishing
            costInput.text.clear()
        } else {
            Toast.makeText(this, "Please enter a valid cost.", Toast.LENGTH_SHORT).show()
        }
    }
}
