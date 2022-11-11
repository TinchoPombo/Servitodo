package com.ort.servitodo.viewmodels.rubro

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Mantenimiento
import com.ort.servitodo.entities.PaseaPerros
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class PaseaPerrosViewModel : ViewModel() {
    private lateinit var view : View
    private lateinit var usuarioRepository : UsuarioRepository
    private var publicacionRepository = PublicacionRepository()
    private  var idRubro = 0
    private lateinit var descripcion : String

    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
        usuarioRepository = UsuarioRepository(v)
    }

    fun setIdRubro( id : Int){
        this.idRubro = id
    }

    fun setDescripcion(descripcion : String){
        this.descripcion = descripcion
    }

    fun validacion(precioPaseo : String, cantPerros : String){
        if(!precioPaseo.isNullOrEmpty() && !cantPerros.isNullOrEmpty()){
            val rubro : Rubro = PaseaPerros(Integer.parseInt(cantPerros), Integer.parseInt(precioPaseo), idRubro, "Mantenimiento")
            crearPublicacion(rubro)
        }else {
            Snackbar.make(view, "Debe completar todos los campos", Snackbar.LENGTH_SHORT).show()}
    }

    private fun crearPublicacion(rubro: Rubro){
        viewModelScope.launch {
            val idPublicacion = (1000000..9999999).random()
            val user = usuarioRepository.getUsuarioById(usuarioRepository.getIdSession())
            val publicacion = Publicacion(idPublicacion, user.id, user.foto, user.nombre, user.apellido, rubro, descripcion)

            publicacionRepository.addPublicacion(publicacion)
            view.findNavController().navigateUp()
        }

    }
}