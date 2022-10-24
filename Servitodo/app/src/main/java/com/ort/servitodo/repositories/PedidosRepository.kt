package com.ort.servitodo.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import kotlinx.coroutines.tasks.await

class PedidosRepository {

    private var listaPedidos: MutableList<Pedido> = mutableListOf()
    val db = Firebase.firestore

    //----------------------------------------------------------------------------------------------
    suspend fun getPedidos(): MutableList<Pedido> {
        val questionRef = db.collection("pedidos")
        var pedidos : MutableList<Pedido> = mutableListOf()
        try {
            val data = questionRef.get().await()
            for (document in data) {
                pedidos.add(document.toObject())
            }
        } catch (e: Exception) { }

        return pedidos
    }

    suspend fun getPedidoByIndex(id: Int): Pedido {

        var pedidoEsperado = Pedido()

        try {
            listaPedidos = getPedidos()
            pedidoEsperado = listaPedidos.elementAt(id)
        } catch (e: Exception) { }

        return pedidoEsperado
    }

    fun addPedido(publicacion : Publicacion, fecha : String, hora : String)
    {
        val idCliente = 1 //--> TODO: Una vez que se loguea se debe obtener el ID del cliente
        val idPedido = 5 //--> TODO: ramdon id para el atributo (no id del documento)

        val pedido = Pedido(idPedido, publicacion.idServicio, publicacion.idPrestador, idCliente, fecha,
            hora, TipoEstado.PENDIENTE.toString(), 0.0)

        //--> Esto se hace para recuperar el documento (en caso de querer usarlo). Se obtiene el obj guardado
        val pedidoGuardado = db.collection("pedidos").document()
        pedidoGuardado.set(pedido)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${pedidoGuardado.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    //----------------------------------------------------------------------------------------------
    /*init{
        listaPedidos.add(
            Pedido(1, 1, 1, "29-10-2022", "8:00", TipoEstado.EN_CURSO.toString()))

        listaPedidos.add(
            Pedido(2, 1, 2, "15-11-2022", "20:00", TipoEstado.APROBADO.toString()))

        listaPedidos.add(
            Pedido(3, 2, 1, "19-10-2022", "8:00", TipoEstado.APROBADO.toString()))

        listaPedidos.add(
            Pedido(4, 2, 3, "19-10-2022", "12:00", TipoEstado.APROBADO.toString()))

        listaPedidos.add(
            Pedido(5, 3, 4, "18-11-2022", "17:00", TipoEstado.APROBADO.toString()))
    }*/

}