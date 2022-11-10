package com.ort.servitodo.entities

class Puntuacion(
    var id: Int,
    var idCliente: String,
    var idPrestador: String,
    var idServicio: Int,
    var puntaje: Float,
    var comentario: String,
        ){
    constructor():this(0,"", "",0, "".toFloat(),"")

    init {
        this.id = id!!
        this.idPrestador = idPrestador!!
        this.idCliente = idCliente!!
        this.idServicio = idServicio!!
        this.puntaje = puntaje!!
        this.comentario = comentario!!

    }
}
