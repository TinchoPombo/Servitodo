package com.ort.servitodo.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import kotlinx.coroutines.tasks.await

class PedidosRepository {

    val db = Firebase.firestore
    private var questionRef = db.collection("pedidos2")

    private var listaPedidos: MutableList<Pedido> = mutableListOf()

    private var calendar = CalendarViewModel()

    //----------------------------------------------------------------------------------------------
    fun changeStateFinalizado(){
        try {
         this.questionRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {

                    val pedido = document.toObject<Pedido>()
                    val cond = calendar.getDateInTimeInMillis(pedido.fecha) < calendar.getTodayInTimeMillis()

                    if(cond){
                        val update: MutableMap<String, Any> = HashMap()
                        update["estado"] = TipoEstado.FINALIZADO.toString()
                        questionRef.document(document.id).set(update, SetOptions.merge())
                    }
                }
            }
         }
        } catch (e: Exception) { }
    }

    //----------------------------------------------------------------
    suspend fun getPedidos(): MutableList<Pedido> {
        try {
            val data = this.questionRef.get().await()
            for (document in data) {
                listaPedidos.add(document.toObject())
            }
        } catch (e: Exception) { }

        return listaPedidos
    }

    suspend fun getPedidosCliente() : MutableList<Pedido>{
        try {
            listaPedidos = getPedidos().filter{ p -> p.estado != TipoEstado.FINALIZADO.toString() && p.estado != TipoEstado.RECHAZADO.toString()}.toMutableList()
        } catch (e: Exception) { }
        return listaPedidos
    }

    suspend fun getHistorialPedidos(): MutableList<Pedido> {
        try {
            listaPedidos = getPedidos().filter{ p -> p.estado == TipoEstado.FINALIZADO.toString() && p.estado == TipoEstado.RECHAZADO.toString()}.toMutableList()
        } catch (e: Exception) { }
        return listaPedidos
    }

    suspend fun getPedidoByIndex(id: Int): Pedido {

        var pedidoEsperado = Pedido()
        var listaPedidos: MutableList<Pedido> = mutableListOf()

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
        val pedidoGuardado = db.collection("pedidos2").document()
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
            Pedido(1, 1, 1, 1, "29-10-2022", "8:00", TipoEstado.EN_CURSO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 2, 2, 1, "15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 3, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 4, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))
        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))
        listaPedidos.add(
            Pedido(2, 2, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 2, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 2, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 4, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 3, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))

        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))
        listaPedidos.add(
            Pedido(2, 4, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))
        listaPedidos.add(
            Pedido(2, 1, 2, 1,"15-11-2022", "20:00", TipoEstado.APROBADO.toString(), 0.0))
    }

    fun getPedidos() : MutableList<Pedido>{
        return listaPedidos
    }*/
}