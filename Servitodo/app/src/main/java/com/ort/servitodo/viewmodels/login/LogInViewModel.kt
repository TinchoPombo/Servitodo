package com.ort.servitodo.viewmodels.login

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInViewModel : ViewModel() {

    lateinit var v: View
    private lateinit var usuarioRepository : UsuarioRepository

    fun setView(v: View) {
        this.v = v
        usuarioRepository = UsuarioRepository(v)
    }

    fun validarCuenta(email : String, pw: String): Usuario {
        var user = Usuario()

        viewModelScope.launch {

            user = withContext(Dispatchers.IO) { usuarioRepository.validarMailYPw(email, pw) }
            Log.d("TestCo", "Hola")
            
        }
        return user
    }

    fun logOut() {
        usuarioRepository.deleteSharedPreferences()
    }

    fun saveSession(id: String, mail: String, password: String) {
        usuarioRepository.setUserCredentialsInSharedPreferences(id, mail, password)
    }


    //val db = Firebase.firestore

    // Create a new user with a first and last name
    /*val user = Usuario(
        id = 1,
        nombre = "Lovelace",
        apellido = "born"
    )*/

    //db.collection("cities").document("new-city-id").set(user)


    // Add a new document with a generated ID
    /*db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
        Log.e("Buenas", documentReference.toString())
        //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }*/

}