package com.ort.servitodo.viewmodels.login

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpViewModel : ViewModel() {


    // Initialize Firebase Auth
    private var auth: FirebaseAuth = Firebase.auth
    lateinit var v: View


    fun setView(v: View) {
        this.v = v
    }

    fun createAccount(email: String, password: String) {

    }
}