package com.ort.servitodo.entities

open class Usuario (
    var id : String,
    var nombre : String,
    var apellido : String,
    var mail : String,
    var password : String,
    var telefono : String,
    var foto : String,
    var ubicacion : String,
    var esPrestador : Boolean
//    var puntuaciones : MutableList<Puntuacion> = mutableListOf(),
//    var listaHistorial : MutableList<Historial> = mutableListOf(),
//
    ){
    constructor():this("0","","","","","","","", false)

    fun gatArrayDatos() :Array<String>{
        return arrayOf(this.nombre, this.apellido, this.ubicacion, this.foto)
    }
}
