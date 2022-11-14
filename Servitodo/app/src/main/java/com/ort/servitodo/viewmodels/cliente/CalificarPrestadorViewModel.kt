package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class CalificarPrestadorViewModel : ViewModel() {
    private lateinit var view : View
    private lateinit var usuarioRepository : UsuarioRepository
    private var repositoryCalificaciones = CalificacionesRepository()


    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
        usuarioRepository = UsuarioRepository(v)
    }



    fun calificar(descripcion: String, rating: Float, pedido : Pedido){
        viewModelScope.launch {

           var tiene = repositoryCalificaciones.hayCalificacionPorPedidoIdCliente(pedido)

            if(!tiene){
                val idCalificacion = (1..99999999).random()
                val user = usuarioRepository.getUsuarioById(usuarioRepository.getIdSession())
                val calificacion = Puntuacion(idCalificacion, user.id,  pedido.idPrestador , pedido.id, rating, descripcion, false)

                db.collection("calificaciones").document().set(calificacion)
               // repositoryCalificaciones.agregarCalificacion(calificacion)
            }else{
                Snackbar.make(view, "No puedes calificar 2 veces un pedido", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }
    }

}