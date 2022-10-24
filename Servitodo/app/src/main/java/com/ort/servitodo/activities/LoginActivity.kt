package com.ort.servitodo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.ort.servitodo.R
import com.ort.servitodo.fragments.rubro.FleteroFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*//add one fragment
        if(findViewById<FrameLayout>(R.id.framelayout) != null){
            supportFragmentManager.beginTransaction().add(R.id.framelayout, FleteroFragment()).commit()
        }*/
    }
}