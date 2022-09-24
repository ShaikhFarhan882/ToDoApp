package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }





/*
    override fun onBackPressed() {
        super.onBackPressed()

        val builder = AlertDialog.Builder(this@MainActivity).apply {
            setTitle("Exit App")
            setMessage("Do yo want to exit from the application")
            setCancelable(false)
            setPositiveButton("Yes") {
                // When the user click yes button then app will close
                    dialog, which -> finish()
            }
            setNegativeButton("No") {
                // When the user click yes button then app will close
                    dialog, which -> dialog.cancel()
            }
        }

        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }*/



    override fun onSupportNavigateUp(): Boolean {
      return navController.navigateUp() || super.onSupportNavigateUp()

    }
}


