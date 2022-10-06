package com.ort.servitodo.entities

class Publicacion (
    var idServicio : Int,
    var idPrestador : Int,
    var fotoPrestador : String,
    var nombrePrestador : String,
    var apellidoPrestador : String,
    var idRubro : Int,
    var nombreRubro : String,
    var listaPuntuaciones : MutableList<Puntuacion> = mutableListOf(),
    var imagenes : MutableList<String> = mutableListOf(),
){
}