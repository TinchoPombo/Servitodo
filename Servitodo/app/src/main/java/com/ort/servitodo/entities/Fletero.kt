package com.ort.servitodo.entities

class Fletero(var costoXHora: Int, var pesoMax: Int, id: Int, nombre: String) :
    Rubro(id, nombre){

    constructor() : this(0,0,0,"")

}