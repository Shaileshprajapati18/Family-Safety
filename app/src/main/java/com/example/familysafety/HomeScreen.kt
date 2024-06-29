package com.example.familysafety

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homescreen)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom.selectedItemId = R.id.home

        inflateFragment(Home.newInstance())

        bottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.guard -> inflateFragment(Guard.newInstance())
                R.id.home -> inflateFragment(Home.newInstance())
                R.id.profile -> inflateFragment(Profile.newInstance())
                R.id.dashboard -> inflateFragment(Dashboard.newInstance())
            }
            true
        }
    }

    private fun inflateFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
