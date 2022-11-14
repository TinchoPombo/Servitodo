package com.ort.servitodo.viewmodels.prestador

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentCrearPublicacionBinding
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.prestador.CrearPublicacionFragmentDirections
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class CrearPublicacionViewModel : ViewModel() {
    private lateinit var view : View
    private lateinit var usuarioRepository : UsuarioRepository
    private lateinit var user : Usuario
    private var publicacionRepository = PublicacionRepository()

    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
        usuarioRepository = UsuarioRepository(v)
    }

    fun getUsuario(){
        viewModelScope.launch {
            user = usuarioRepository.getUsuarioById(usuarioRepository.getIdSession())
        }
    }

    fun verificarPublicacion(binding: FragmentCrearPublicacionBinding) {
        viewModelScope.launch {
           if(!publicacionRepository.existePublicacion(binding.autoCompleteTextView.text.toString(), user.id)){

               val descripcion = binding.txtDireccion.text.toString()

               val cond = !descripcion.isNullOrEmpty()
               if(cond) {
                   when(binding.autoCompleteTextView.text.toString()){
                       "Mantenimiento" -> view.findNavController().navigate( CrearPublicacionFragmentDirections.actionCrearPublicacionFragmentToMantenimientoFragment(descripcion,1))
                       "Fletero" -> view.findNavController().navigate( CrearPublicacionFragmentDirections.actionCrearPublicacionFragmentToFleteroFragment(descripcion,2))
                       "Pasea perros" -> view.findNavController().navigate( CrearPublicacionFragmentDirections.actionCrearPublicacionFragmentToPaseaPerrosFragment(descripcion,3))
                   }
               } else {
                   Snackbar.make(view, "Debe completar la descripcion", Snackbar.LENGTH_SHORT).show()
               }
           }else{
               Snackbar.make(view, "Ya tiene una publicacion de " + binding.autoCompleteTextView.text.toString(), Snackbar.LENGTH_SHORT).show()
           }
        }
    }

}