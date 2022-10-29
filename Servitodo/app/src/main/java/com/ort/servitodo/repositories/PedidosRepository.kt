package com.ort.servitodo.repositories

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PedidosRepository {

    val db = Firebase.firestore

    var listaPedidos: MutableList<Pedido> = mutableListOf()

    //----------------------------------------------------------------------------------------------
    //fun getPedidosFromDB () : MutableList<Pedido>{

    /*fun getPedidos() : MutableList<Pedido> {
        var listaPedidos: MutableList<Pedido> = mutableListOf()
        db.collection("pedidos").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (publicacion in snapshot) {
                        listaPedidos.add(publicacion.toObject())
                    }
                    Log.d("tamaniolistapedidos1", "${listaPedidos.size}")
                }
                this.listaPedidos = listaPedidos
                Log.d("tamaniolistapedidos2", "${listaPedidos.size}")
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        val col = db.collection("pedidos").addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                //val cities = ArrayList<String>()
                for (doc in value!!) {
                    //doc.getString("name")?.let {
                        listaPedidos.add(doc.toObject())
                    //}
                }
                Log.d("pedidosConSnapshotListener", "Current pedidos in CA: $listaPedidos")
            }
        Log.d("tamaniolistapedidosConSnapList", "${listaPedidos.size}")

        return listaPedidos
    }*/

    /*fun getPedidos() : MutableList<Pedido>{
        val parent = Job()
        val scope = CoroutineScope(Dispatchers.Main + parent)
        var p : MutableList<Pedido> = mutableListOf()
        scope.launch(){
            getPedidosFromDB()
            p = listaPedidos
        }
        return p
    }*/

    suspend fun getPedidos(): MutableList<Pedido> {
        val questionRef = db.collection("pedidos")
        //var pedidos : MutableList<Pedido> = mutableListOf()
        try {
            val data = questionRef.get().await()
            for (document in data) {
                listaPedidos.add(document.toObject())
            }
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