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
import com.ort.servitodo.repositories.CalificacionesRepository
import kotlinx.coroutines.launch

class OpinionesPrestadorViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = CalificacionesRepository()
    private lateinit var puntuacion: Puntuacion
    val cargando = MutableLiveData<String>()
    var calificaciones : MutableList<Puntuacion> = arrayListOf()
    private var id = ""
    private lateinit var recycler : RecyclerView



    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

     fun create () : Puntuacion{
        return Puntuacion(1, "1", "1" , 1, "2".toFloat(), "ASd")
    }

    fun emptyList(){
        this.calificaciones.clear()
    }


    fun recyclerView(view : View, id : String){

        cargando.value = "Cargando..."

        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_opiniones_prestador)
        this.recycler = dialog.findViewById(R.id.opinionesPrestadorRecycler)!!


        viewModelScope.launch{

           /*calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())*/

          // calificaciones = repository.getCalificacionesByClienteId(id)
            calificaciones = repository.getCalificaciones()

            if(calificaciones.size < 1) {
                cargando.value = "No hay calificaciones disponibles"
            }
            else{
                recycler.setHasFixedSize(true)

                cargando.value = ""

                recycler.layoutManager  = LinearLayoutManager(view.context)

                 recycler.adapter = CalificacionesAdapter(calificaciones){}



                  }
            dialog.show()
        }

    }


}






