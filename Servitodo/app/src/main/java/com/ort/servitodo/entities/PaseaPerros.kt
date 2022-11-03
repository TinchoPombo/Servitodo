package com.ort.servitodo.entities

class PaseaPerros (var cantPerros : Int, var precioPaseo : Int, id: Int, nombre: String) :
    Rubro(id, nombre) {

    constructor() : this(0,0,0,"")

    override fun toString() : String{
        return "Cantidad de perros: ${cantPerros} \n Precio del paseo: $${precioPaseo}"
    }

}