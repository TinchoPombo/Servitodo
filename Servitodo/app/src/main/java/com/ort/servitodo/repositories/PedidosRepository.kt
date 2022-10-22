package com.ort.servitodo.repositories

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

    suspend fun getPedidos(): MutableList<Pedido> {
        val questionRef = db.collection("pedidos")
        var pedidos : MutableList<Pedido> = mutableListOf()
        try {
            val data = questionRef.get().await()
            for (document in data) {
                pedidos.add(document.toObject())
            }
        } catch (e: Exception) {
        }

        return pedidos
    }

    /*var id : Int = 0
    var idPublicacion : Int = 0
    var idCliente : Int = 0
    var fecha : String
    var hora : String
    var estado : String*/

    /*suspend fun getPedidos() : MutableList<Pedido>{

        //var publicacion = PublicacionRepository()

        try {
            listaPedidos = getPedidosFromDB()
            for(p in listaPedidos){
                val publicacion = PublicacionRepository().getPublicacionById(p.idPublicacion)

            }
        }
        catch(e: Exception){ }

        return listaPedidos
    }*/

    suspend fun getPedidoByIndex(id: Int): Pedido {

        var pedidoEsperado = Pedido()

        try {
            listaPedidos = getPedidos()
            pedidoEsperado = listaPedidos.elementAt(id)
        } catch (e: Exception) {
        }

        return pedidoEsperado
    }
}