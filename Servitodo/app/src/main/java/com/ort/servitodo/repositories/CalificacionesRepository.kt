package com.ort.servitodo.repositories

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Puntuacion
import kotlinx.coroutines.tasks.await

class CalificacionesRepository {

    val db = Firebase.firestore
    val questionRef = db.collection("calificaciones")

    private var listaCalificaciones: MutableList<Puntuacion> = mutableListOf()

    suspend fun getCalificaciones(): MutableList<Puntuacion> {

        try {
            val data = this.questionRef.get().await()
            for (document in data) {
                listaCalificaciones.add(document.toObject())

            }
        } catch (e: Exception) {
            Log.d("Calificaciones", "${listaCalificaciones} ")
        }

        return listaCalificaciones
    }

    suspend fun getCalificacionesByClienteId(id : String)  : MutableList<Puntuacion> {
        var listaCalificaciones : MutableList<Puntuacion> = mutableListOf()

        try{
            val data = this.questionRef.get().await()
            for (document in data) {
                if(document.toObject<Puntuacion>().idCliente == id && document.toObject<Puntuacion>().calificoPrestador){
                    listaCalificaciones.add(document.toObject())
                }
            }
        }catch(e : Exception){ }

        return listaCalificaciones
    }

    suspend fun getCalificacionesByPrestadorId(id : String)  : MutableList<Puntuacion> {
        var listaCalificaciones : MutableList<Puntuacion> = mutableListOf()

        try{
            val data = this.questionRef.get().await()
            for (document in data) {
                if(document.toObject<Puntuacion>().idPrestador == id && !document.toObject<Puntuacion>().calificoPrestador){
                    listaCalificaciones.add(document.toObject())
                }
            }

        }catch(e : Exception){ }

        return listaCalificaciones
    }

    suspend fun hayCalificacionPorPedidoIdCliente(pedido : Pedido) : Boolean {
        var tiene = false

        try{
            val data = this.questionRef.get().await()
            for (document in data) {
                if(document.toObject<Puntuacion>().idPrestador == pedido.idPrestador && document.toObject<Puntuacion>().idCliente == pedido.idCliente && document.toObject<Puntuacion>().idPedido == pedido.id && !document.toObject<Puntuacion>().calificoPrestador){
                    tiene = true
                }
            }
        }catch(e : Exception){ }

        return tiene
    }

    suspend fun hayCalificacionPorPedidoIdPrestador(pedido : Pedido) : Boolean {
        var tiene = false

        try{
            val data = this.questionRef.get().await()
            for (document in data) {
                if(document.toObject<Puntuacion>().idPrestador == pedido.idPrestador && document.toObject<Puntuacion>().idCliente == pedido.idCliente && document.toObject<Puntuacion>().idPedido == pedido.id && document.toObject<Puntuacion>().calificoPrestador){
                    tiene = true
                }
            }
        }catch(e : Exception){ }

        return tiene
    }

    fun agregarCalificacion(puntuacion : Puntuacion ){
        db.collection("calificaciones").document(puntuacion.id.toString()).set(puntuacion)

    }
}

    /*  fun uppCalificacion(id: String, cali : Puntuacion){
            questionRef.document(id).set(cali)
    }*/
