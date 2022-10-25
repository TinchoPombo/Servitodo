package com.ort.servitodo.entities

enum class TipoEstado {
    EN_CURSO, PENDIENTE, RECHAZADO, APROBADO, RESERVADO, FINALIZADO

    //Pendiente: el cliente pidio el servicio, pero todavia no fue aceptado por el prestador
    //Reservado: el prestador acepta el servicio, se habilita wp, y se puede modificar la fecha
    //Aprobado: el prestador acepta el servicio definitivamente, con el horario definido.

}