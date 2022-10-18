package com.ort.servitodo.repositories

import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.TipoEstado

class PedidosRepository {

    private var listaPedidos : MutableList<Pedido> = mutableListOf()

    init{
        listaPedidos.add(
            Pedido(1, 1, 1, "29-10-2022", "8:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(11, 1, 3, "29-10-2022", "9:00", TipoEstado.RECHAZADO))

        listaPedidos.add(
            Pedido(2, 1, 2, "15-11-2022", "20:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(3, 2, 1, "19-10-2022", "8:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(4, 2, 3, "19-10-2022", "12:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(5, 3, 4, "18-11-2022", "17:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(6, 3, 5, "30-11-2022", "18:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(7, 4, 4, "25-11-2022", "8:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(8, 4, 6, "25-11-2022", "19:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(9, 5, 6, "29-10-2022", "20:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(10, 6, 2, "21-12-2022", "11:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(12, 1, 1, "1-11-2022", "8:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(13, 1, 1, "1-11-2022", "9:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(14, 1, 1, "1-11-2022", "10:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(15, 1, 1, "1-11-2022", "11:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(16, 1, 1, "1-11-2022", "12:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(17, 1, 1, "1-11-2022", "13:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(18, 1, 1, "1-11-2022", "14:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(19, 1, 1, "1-11-2022", "15:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(20, 1, 1, "1-11-2022", "16:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(21, 1, 1, "1-11-2022", "17:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(22, 1, 1, "1-11-2022", "18:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(23, 1, 1, "1-11-2022", "19:00", TipoEstado.APROBADO))

        listaPedidos.add(
            Pedido(24, 1, 1, "1-11-2022", "20:00", TipoEstado.APROBADO))

    }

    fun getPedidos () : MutableList<Pedido>{
        return listaPedidos
    }
}