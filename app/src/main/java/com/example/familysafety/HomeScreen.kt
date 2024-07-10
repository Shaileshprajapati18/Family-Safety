package com.example.familysafety

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreen : AppCompatActivity() {

    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_CONTACTS
    )

    val permissionsRequestCode = 78

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homescreen)

        askForPermission()

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom.selectedItemId = R.id.home

        inflateFragment(Home.newInstance())

        bottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.guard -> inflateFragment(Guard.newInstance())
                R.id.home -> inflateFragment(Home.newInstance())
                R.id.profile -> inflateFragment(Profile.newInstance())
                R.id.dashboard -> inflateFragment(MapsFragment())
            }
            true
        }
    }

    private fun askForPermission() {

        ActivityCompat.requestPermissions(this, permissions, permissionsRequestCode)

    }

    private fun inflateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newInstance)
        transaction.commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionsRequestCode) {

            if (allPermissionGranted()) {
                openCamera()

            } else {

            }
        }
    }

    private fun openCamera() {
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    private fun allPermissionGranted(): Boolean {

        for (item in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    item
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}
