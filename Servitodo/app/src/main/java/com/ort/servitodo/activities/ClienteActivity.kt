package com.ort.servitodo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ort.servitodo.R

class ClienteActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        bottomNavView = findViewById(R.id.bottom_bar_cliente)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_cliente) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }
}