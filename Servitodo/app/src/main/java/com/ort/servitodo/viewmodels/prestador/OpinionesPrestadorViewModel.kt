package com.ort.servitodo.viewmodels.prestador

import android.app.Dialog
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ort.servitodo.R
import com.ort.servitodo.adapters.CalificacionesAdapter
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class OpinionesPrestadorViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = CalificacionesRepository()
    private lateinit var puntuacion: Puntuacion
    val cargando = MutableLiveData<String>()
    var calificaciones : MutableList<Puntuacion> = arrayListOf()
    private var id = ""
    private lateinit var recycler : RecyclerView
    private lateinit var repositoryUser : UsuarioRepository

    fun setView(v : View){
        this.view = v

    }

    fun emptyList(){
        this.calificaciones.clear()
    }


    fun recyclerView(view : View, id : String){

        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_opiniones_prestador)
        recycler = dialog.findViewById(R.id.opinionesPrestadorRecycler)!!


        viewModelScope.launch{

            repositoryUser = UsuarioRepository(view)
            val userActual : Usuario = repositoryUser.getUsuarioById(repositoryUser.getIdSession())
            val esPrestador : Boolean = userActual.esPrestador
            calificaciones = repository.getCalificacionesByClienteId(id)

            recycler.setHasFixedSize(true)
            recycler.layoutManager  = LinearLayoutManager(view.context)
            recycler.adapter = CalificacionesAdapter(calificaciones, esPrestador){}
        }
        dialog.show()

    }


}






