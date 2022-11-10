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
import com.ort.servitodo.repositories.CalificacionesRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OpinionesClienteViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = CalificacionesRepository()
    private lateinit var puntuacion: Puntuacion
    val cargando = MutableLiveData<String>()
    var calificaciones : MutableList<Puntuacion> = arrayListOf()
    var id : String =""

    val db = Firebase.firestore
    private var questionRef = db.collection("calificaciones")



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

    /*fun opinionesDelPrestador(view : View, id :String): RecyclerView{
        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_opiniones_cliente)

        val recycler = dialog.findViewById<RecyclerView>(R.id.opinionesClienteRecycler)!!
        dialog.show()
        this.id = id
        return recycler
    }*/


    fun recyclerView(view : View, id : String){

        cargando.value = "Cargando..."





        viewModelScope.launch{

            val dialog = BottomSheetDialog(view.context)
            dialog.setContentView(R.layout.fragment_opiniones_cliente)

            val recycler = dialog.findViewById<RecyclerView>(R.id.opinionesClienteRecycler)!!
            /*calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())
            calificaciones.add(create())


             */
            //calificaciones = repository.getCalificacionesByPrestadorId(id)
            calificaciones = repository.getCalificaciones()

            if(calificaciones.size < 1) {
                cargando.value = "No hay calificaciones disponibles"
            }
            else{
                recycler.setHasFixedSize(true)

                cargando.value = ""

                recycler.layoutManager  = LinearLayoutManager(view.context)

                recycler.adapter = CalificacionesAdapter(calificaciones){

                }



            }
            dialog.show()
        }


    }

}