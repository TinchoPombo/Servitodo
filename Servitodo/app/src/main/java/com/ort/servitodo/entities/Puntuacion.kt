package com.ort.servitodo.entities

class Puntuacion(
    var id: Int,
    var idCliente: String,
    var idPrestador: String,
    var idPedido: Int,
    var puntaje: Float,
    var comentario: String,
    var calificoPrestador: Boolean
        ){
    constructor():this(0,"", "",0, 0f,"", false)

    init {
        this.id = id!!
        this.idPrestador = idPrestador!!
        this.idCliente = idCliente!!
        this.idPedido = idPedido!!
        this.puntaje = puntaje!!
        this.comentario = comentario!!
        this.calificoPrestador = calificoPrestador

    }
}
