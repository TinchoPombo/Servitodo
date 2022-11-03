package com.ort.servitodo.entities

class Mantenimiento (var precioConsulta : Int, id: Int, nombre: String) :
    Rubro(id, nombre) {

    constructor() : this(0,0,"")

    override fun toString() : String{
        return "Precio de la consulta: $${precioConsulta}"
    }

}