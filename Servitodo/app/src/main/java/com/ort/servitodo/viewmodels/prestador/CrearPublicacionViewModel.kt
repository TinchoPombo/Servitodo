package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PrestadorRepository
import com.ort.servitodo.repositories.PublicacionRepository
import kotlinx.coroutines.launch

class CrearPublicacionViewModel : ViewModel() {
    private lateinit var view : View
    private var repository = PublicacionRepository()
    private var publicaciones : MutableList<Publicacion> = arrayListOf()
    val db = Firebase.firestore


    fun setView(v : View){
        this.view = v
    }

    fun emptyList(){
        this.publicaciones.clear()
    }

    fun getPublicaciones(): MutableList<Publicacion> {

        viewModelScope.launch{
            publicaciones = repository.getPublicaciones()
        }
        return publicaciones
    }

}