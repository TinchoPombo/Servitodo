package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class CrearPublicacionViewModel : ViewModel() {
    private lateinit var view : View
    private var repository = PublicacionRepository()
    private lateinit var usuarioRepository : UsuarioRepository

    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
        usuarioRepository = UsuarioRepository(v)
    }

    fun crearPublicacion(descripcion: String, rubro: Rubro){
        viewModelScope.launch {
            val idPublicacion = repository.getSize() + 1
            val user = usuarioRepository.getUsuarioById(usuarioRepository.getIdSession())
            val publicacion = Publicacion(idPublicacion, user.id, user.foto, user.nombre, user.apellido, rubro, descripcion)

            db.collection("publicaciones").document(idPublicacion.toString()).set(publicacion)
        }
    }

}