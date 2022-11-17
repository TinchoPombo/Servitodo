package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.fragments.prestador.CalificarClienteFragmentDirections
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class CalificarClienteViewModel : ViewModel() {
    private lateinit var view : View
    private var repository = PedidosRepository()
    private lateinit var usuarioRepository : UsuarioRepository
    private var repositoryCalificaciones = CalificacionesRepository()

    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
        usuarioRepository = UsuarioRepository(v)
    }



    fun calificar(descripcion: String, rating: Float,pedido: Pedido){


        viewModelScope.launch {

          val tiene =  repositoryCalificaciones.hayCalificacionPorPedidoIdPrestador(pedido)

           if(!tiene){
                val idCalificacion = (1..99999999).random()
                val user = usuarioRepository.getUsuarioById(usuarioRepository.getIdSession())
                val calificacion = Puntuacion(idCalificacion, pedido.idCliente, user.id , pedido.id, rating, descripcion, true)

               db.collection("calificaciones").document().set(calificacion)
               view.findNavController().navigate(CalificarClienteFragmentDirections.actionCalificarClienteFragmentToHomePrestadorFragment())

            }else{
               Snackbar.make(view, "No puedes calificar 2 veces un pedido", Snackbar.LENGTH_SHORT)
                   .show()
            }

        }
    }

}