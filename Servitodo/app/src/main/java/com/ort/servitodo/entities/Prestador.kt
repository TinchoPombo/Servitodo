package com.ort.servitodo.entities

class Prestador (
    var serviciosPublicados : MutableList<Publicacion> = mutableListOf(),
    var pedidosPendientes : MutableList<Pedido> = mutableListOf(),
    var pedidosAceptados : MutableList<Pedido> = mutableListOf(),
) {
}