package com.ort.servitodo.viewmodels.login

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository

class SignUpViewModel : ViewModel() {


    // Initialize Firebase Auth
    private var auth: FirebaseAuth = Firebase.auth
    lateinit var v: View
    private lateinit var usuarioRepository : UsuarioRepository



    fun setView(v: View) {
        this.v = v
        usuarioRepository = UsuarioRepository(v)
    }

    fun createAccount(email: String, password: String) {

    }

    fun addUsuario(
        nombre : String,
        apellido :String,
        mail : String,
        password: String,
        telefono : String,
        foto : String,
        ubicacion : String,
        esPrestador: Boolean
    ) {
       usuarioRepository.addUsuario(Usuario(
           232,
           nombre,
           apellido,
           mail,
           password,
           telefono,
           foto,
           ubicacion,
           esPrestador))
    }
}