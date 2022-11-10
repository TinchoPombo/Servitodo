package com.ort.servitodo.repositories

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Puntuacion
import kotlinx.coroutines.tasks.await

class CalificacionesRepository {

    val db = Firebase.firestore
    private var questionRef = db.collection("calificaciones")

    private var listaCalificaciones: MutableList<Puntuacion> = mutableListOf()

    suspend fun getCalificaciones(): MutableList<Puntuacion> {


        try {
            val data = this.questionRef.get().await()
            for (document in data) {
                val puntuacion : Puntuacion = document.toObject()
                listaCalificaciones.add(puntuacion)
            }
        } catch (e: Exception) {
            Log.d("Calificaciones DB", "calificaciones encotrados ${listaCalificaciones} ")
        }

        return listaCalificaciones
    }

    suspend fun getCalificacionesByClienteId(id : String)  : MutableList<Puntuacion> {
        var listaCalificaciones : MutableList<Puntuacion> = mutableListOf()

        try{
            listaCalificaciones = getCalificaciones().filter { c -> c.idCliente == id  }.toMutableList()
        }catch(e : Exception){ }

        return listaCalificaciones
    }

    suspend fun getCalificacionesByPrestadorId(id : String)  : MutableList<Puntuacion> {
        var listaCalificaciones : MutableList<Puntuacion> = mutableListOf()

        try{
            listaCalificaciones = getCalificaciones().filter { c -> c.idPrestador == id  }.toMutableList()
        }catch(e : Exception){ }

        return listaCalificaciones
    }

        fun uppCalificacion(id: String, cali : Puntuacion){
            questionRef.document(id).set(cali)
    }
}