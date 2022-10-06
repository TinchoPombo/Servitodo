package com.ort.servitodo.entities

import java.text.DateFormat

class Pedido (
    var id : Int,
    var idServicio : Int,
    var idCliente : Int,
    var fecha : DateFormat,
    var estado : Int,

    // var horario????
        ){
}