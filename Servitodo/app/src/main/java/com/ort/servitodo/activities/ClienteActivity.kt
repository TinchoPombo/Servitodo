package com.ort.servitodo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R

class ClienteActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

/*    val db = Firebase.firestore

    // Create a new user with a first and last name
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )


    // Add a new document with a generated ID
    val s = db.collection("users")
    .add(user)
    .addOnSuccessListener { documentReference ->
        //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
    }
    .addOnFailureListener { e ->
        //Log.w(TAG, "Error adding document", e)
    }*/

    //Get
    /*val asda = db.collection("users")
    .get()
    .addOnSuccessListener { result ->
        for (document in result) {
            //Log.d(TAG, "${document.id} => ${document.data}")
        }
    }
    .addOnFailureListener { exception ->
        //Log.w(TAG, "Error getting documents.", exception)
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        bottomNavView = findViewById(R.id.bottom_bar_cliente)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_cliente) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }
}