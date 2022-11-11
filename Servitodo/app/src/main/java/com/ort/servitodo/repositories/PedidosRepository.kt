package com.ort.servitodo.repositories

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import kotlinx.coroutines.tasks.await
import java.lang.AssertionError
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PedidosRepository {

    val db = Firebase.firestore
    private var questionRef = db.collection("pedidos")

    private var listaPedidos: MutableList<Pedido> = mutableListOf()

    private var calendar = CalendarViewModel()

    //----------------------------------------------------------------------------------------------
    fun changeState(){
        try {
            this.questionRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {

                        val pedido = document.toObject<Pedido>()

                        val horaPedido = calendar.getOnlyHour(pedido.hora)
                        val diaPedido = calendar.getDateInTimeInMillis(pedido.fecha)
                        val hoy = calendar.getTodayInTimeMillis()
                        val horaAhora = calendar.getHourNow()

                        val cond =  diaPedido < hoy
                        val cond2 = diaPedido == hoy && horaPedido < horaAhora
                        val cond3 = diaPedido == hoy && horaPedido == horaAhora
                        val aprobado = pedido.estado == TipoEstado.APROBADO.toString()
                        val pendiente = pedido.estado == TipoEstado.PENDIENTE.toString()

                        val update: MutableMap<String, Any> = HashMap()
                        if(cond3 && aprobado){
                            update["estado"] = TipoEstado.EN_CURSO.toString()
                        }
                        else if((cond || cond2) && aprobado){
                            update["estado"] = TipoEstado.FINALIZADO.toString()
                        }
                        else if(cond && pendiente){
                            update["estado"] = TipoEstado.RECHAZADO.toString()
                        }
                        questionRef.document(document.id).set(update, SetOptions.merge())
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
        Log.d("PEDIDOS DB", "pedidos encotrados ${listaPedidos} ")
        return listaPedidos
    }


    suspend fun getPedidosCliente(userId : String) : MutableList<Pedido>{
        try {
            val allPedidos = getPedidos().filter{ p -> p.estado != TipoEstado.FINALIZADO.toString()
                    && p.estado != TipoEstado.RECHAZADO.toString()
                    && p.estado != TipoEstado.CANCELADO.toString()
            }.toMutableList()
            listaPedidos = allPedidos.filter { x -> x.idCliente == userId }.toMutableList()
        } catch (e: Exception) { }
        return listaPedidos
    }


    suspend fun getHistorialPedidos(): MutableList<Pedido> {
        try {
            listaPedidos = getPedidos().filter{ p -> p.estado == TipoEstado.FINALIZADO.toString()
                    && p.estado == TipoEstado.RECHAZADO.toString()
                    && p.estado == TipoEstado.CANCELADO.toString()}
                .toMutableList()
        } catch (e: Exception) { }
        return listaPedidos
    }

    suspend fun getPedidoById(id: Int): Pedido {
        var pedidoEsperado = Pedido()
        var listaPedidos: MutableList<Pedido> = mutableListOf()

        try {
            listaPedidos = getPedidos()
            pedidoEsperado = listaPedidos.find { p -> p.id == id }!!
        } catch (e: Exception) { }

        return pedidoEsperado
    }

    suspend fun getPedidoByIndex(pos: Int): Pedido {
        var pedidoEsperado = Pedido()
        var listaPedidos: MutableList<Pedido> = mutableListOf()

        try {
            listaPedidos = getPedidos()
            pedidoEsperado = listaPedidos.elementAt(pos)
        } catch (e: Exception) { }

        return pedidoEsperado
    }

    suspend fun getPedidosByUserIndex(id : String) : MutableList<Pedido>{
        var listaPedidos : MutableList<Pedido> = mutableListOf()

        try{

            val data = this.questionRef.get().await()

            for (document in data) {
                if(document.toObject<Pedido>().idPrestador == id || document.toObject<Puntuacion>().idCliente == id){
                    listaPedidos.add(document.toObject())
                }
            }

           // listaPedidos = getPedidos().filter { p -> p.idPrestador == id || p.idCliente == id }.toMutableList()
            }catch (e:Exception){}

        return listaPedidos
    }


    //----------------------------------------------------------------
    suspend fun getPedidosPendientesByPrestadorId(idPrestador: String):MutableList<Pedido> {
        try {
            listaPedidos = getPedidos().filter{ p -> p.idPrestador == idPrestador && p.estado == TipoEstado.PENDIENTE.toString()}.toMutableList()
        } catch (e: Exception) { }
        return listaPedidos
    }


    suspend fun getPedidosAprobadosByPrestadorId(idPrestador: String):MutableList<Pedido> {
        try {
            listaPedidos = getPedidos().filter {  p -> p.idPrestador == idPrestador && (p.estado == TipoEstado.APROBADO.toString() || p.estado == TipoEstado.EN_CURSO.toString() ) }.toMutableList()

        } catch (e: Exception) { }
        Log.d("PEDIDOS APROBADOS PRESTADOR", "usuarios encotrados ${listaPedidos} ")
        return listaPedidos
    }

    //----------------------------------------------------------------
    fun addPedido(publicacion : Publicacion, fecha : String, hora : String, idCliente : String) {
        val idPedido = (1000000..9999999).random()

        val pedido = Pedido(idPedido, publicacion.idServicio, publicacion.idPrestador, idCliente, fecha,
            hora, TipoEstado.PENDIENTE.toString(), 0.0)

        val pedidoGuardado = db.collection("pedidos").document(pedido.id.toString()).set(pedido)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${pedido.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun cancelPedido(pedidoId : Int) {
        try {
            this.questionRef.document("${pedidoId}").get().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val update: MutableMap<String, Any> = HashMap()
                    update["estado"] = TipoEstado.CANCELADO.toString()
                    questionRef.document("${pedidoId}").set(update, SetOptions.merge())
                }
            }
        } catch (e: Exception) { }
    }

    suspend fun getPedidosByEstado(id :String, estado : String) : MutableList<Pedido>{

        try{
            listaPedidos = getPedidos().filter { (it.idPrestador == id || it.idCliente == id) && it.estado == estado  }.toMutableList()
        }catch (e :Exception){ }

        return listaPedidos
    }

    suspend fun getPedidoPorFecha(id: String) : MutableList<Pedido>{

        try{

            listaPedidos = getPedidos().filter { it.idCliente == id || it.idPrestador == id }.toMutableList()
        }catch(e:Exception){}
        return listaPedidos
    }


}

