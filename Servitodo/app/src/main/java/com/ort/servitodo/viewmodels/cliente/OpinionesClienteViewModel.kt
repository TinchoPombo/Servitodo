package com.ort.servitodo.viewmodels.cliente

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.adapters.CalificacionesAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OpinionesClienteViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = CalificacionesRepository()
    private lateinit var puntuacion: Puntuacion
    val calificaciones = MutableLiveData<MutableList<Puntuacion>>()
    var id : String =""
    private lateinit var repositoryUser : UsuarioRepository



    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v

    }

    fun emptyList(){
        this.calificaciones.value?.clear()
    }


    fun recyclerView(view : View, id : String){

        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_opiniones_cliente)

        val recycler = dialog.findViewById<RecyclerView>(R.id.opinionesClienteRecycler)!!

        viewModelScope.launch{

            repositoryUser = UsuarioRepository(view)
            val userActual : Usuario = repositoryUser.getUsuarioById(repositoryUser.getIdSession())
            val esPrestador : Boolean = userActual.esPrestador
            calificaciones.value = repository.getCalificacionesByPrestadorId(id)

            recycler.setHasFixedSize(true)
            recycler.layoutManager  = LinearLayoutManager(view.context)

            recycler.adapter = CalificacionesAdapter(calificaciones.value!!, esPrestador ){}
        }
        dialog.show()
    }

}