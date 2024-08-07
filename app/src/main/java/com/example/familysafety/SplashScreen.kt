package com.example.familysafety

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.familysafety.PrefConstants.IS_USER_LOGGED_IN

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isUserLoggedIn= SharePref.getBoolean(IS_USER_LOGGED_IN)

        if (isUserLoggedIn) {
            startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }else {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}