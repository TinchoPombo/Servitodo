package com.ort.servitodo.entities

class Pedido (id : Int, idServicio : Int, idCliente : Int, fecha : String, hora : String, estado : TipoEstado)
{
    var id : Int
    var idServicio : Int
    var idCliente : Int
    var fecha : String
    var hora : String
    var estado : TipoEstado

    init {
        this.id = id
        this.idServicio = idServicio
        this.idCliente = idCliente
        this.fecha = fecha
        this.hora = hora
        this.estado = estado
    }
}