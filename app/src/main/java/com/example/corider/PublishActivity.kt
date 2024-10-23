package com.example.corider // Replace with your actual package name

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class PublishActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var startLatitude: Double? = null
    private var startLongitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish2) // Ensure this matches your layout file name

        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()

        // Enable JavaScript
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // Add JavaScript interface
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidInterface")

        // Load the local HTML file
        webView.loadUrl("file:///android_asset/index.html")

        // Enable debugging (optional)
        WebView.setWebContentsDebuggingEnabled(true)
    }

    // JavaScript Interface class
    private inner class WebAppInterface(private val activity: PublishActivity) {
        @JavascriptInterface
        fun storeLocation(lat: Double, lon: Double) {
            activity.storeLocation(lat, lon)
        }

        @JavascriptInterface
        fun goToNextActivity() {
            val intent = Intent(activity, toDestination::class.java) // Ensure this is your actual destination Activity
            activity.startActivity(intent)
        }
    }

    private fun storeLocation(lat: Double, lon: Double) {
        startLatitude = lat
        startLongitude = lon

        // Save location to SharedPreferences
        val sharedPreferences = getSharedPreferences("RideDetails", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putFloat("start_latitude", lat.toFloat())
            putFloat("start_longitude", lon.toFloat())
            apply() // Commit changes
        }

        Log.d("PublishActivity", "Location stored: Start Latitude = $startLatitude, Start Longitude = $startLongitude")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
