package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.repositories.PedidosRepository

import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class CalificarPrestadorViewModel : ViewModel() {
    private lateinit var view : View
    private var repository = PedidosRepository()
    private lateinit var usuarioRepository : UsuarioRepository

    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
        usuarioRepository = UsuarioRepository(v)
    }

    fun calificar(descripcion: String, rating: Float, idPrestador :String, idPublicacion :Int){
        viewModelScope.launch {
            val idCalificacion = (1000000..9999999).random()
            val user = usuarioRepository.getUsuarioById(usuarioRepository.getIdSession())
            val calificacion = Puntuacion(idCalificacion, user.id,  idPrestador , idPublicacion, rating, descripcion)

            db.collection("calificaciones").document(idCalificacion.toString()).set(calificacion)
        }
    }

}