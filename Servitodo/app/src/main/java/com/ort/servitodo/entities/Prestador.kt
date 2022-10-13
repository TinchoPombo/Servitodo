package com.ort.servitodo.entities

class Prestador (id : Int, username : String, name : String, lastname : String, birth: String, img : String,
                 matricula : String, rubro : String, numtel : String)
{
    //var serviciosPublicados : MutableList<Publicacion> = mutableListOf()
    //var pedidosPendientes : MutableList<Pedido> = mutableListOf()
    //var pedidosAceptados : MutableList<Pedido> = mutableListOf()

    var id : Int
    var username : String
    var name : String
    var lastname : String
    var birth : String
    var img : String
    var rubro : String
    var matricula : String
    var numtel : String

    init {
        this.id = id
        this.username = username
        this.name = name
        this.lastname = lastname
        this.birth = birth
        this.img = img
        this.rubro = rubro
        this.matricula = matricula
        this.numtel = numtel
    }

}