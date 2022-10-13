package com.ort.servitodo.repositories

import com.ort.servitodo.entities.Prestador

class PrestadorRepository {

    private var listaPrestadores : MutableList<Prestador> = mutableListOf()

    init{
        listaPrestadores.add(Prestador(
            1,
            "jorgeroque99",
            "Jorge",
            "Roque",
            "9/9/1988",
            "https://resizer.glanacion.com/resizer/WW93VSwX4xU-ghvAwRFP9IqJOl4=/768x0/filters:format(webp):quality(80)/cloudfront-us-east-1.images.arcpublishing.com/lanacionar/FCDVQTKZZJH6HLYPS5D5ZBNTCY.jpg",
            "12345",
            "Plomero",
            "5491122766971"))

        listaPrestadores.add(Prestador(
            2,
            "pedrogomez10",
            "Pedro",
            "Gomez",
            "10/10/1988",
            "https://i.bssl.es/miusyk/2012/05/Metallica-James-Hetfield.jpg",
            "23456",
            "Gasista",
            "5491139004865"))

        listaPrestadores.add(Prestador(
            3,
            "carlossanchez",
            "Carlos",
            "Sanchez",
            "11/11/1988",
            "https://www.altonivel.com.mx/wp-content/uploads/2021/11/carlos-slim.jpg",
            "34567",
            "Mecanico",
            "5491162139267"))

        listaPrestadores.add(Prestador(
            4,
            "raulrichi",
            "Raul",
            "Richi",
            "12/12/1988",
            "https://static-cdn.jtvnw.net/jtv_user_pictures/574228be-01ef-4eab-bc0e-a4f6b68bedba-profile_image-300x300.png",
            "45678",
            "Pintor",
            "5491137969559"))

        listaPrestadores.add(Prestador(
            5,
            "tomasmuno",
            "Tomas",
            "Muno",
            "1/1/1975",
            "https://cpad.ask.fm/846/535/880/910003015-1qn3b2f-71rh9nlgnba3l3a/original/file.jpg",
            "56789",
            "Paseador de perros",
            "5491156537982"))

        listaPrestadores.add(Prestador(
            6,
            "pedroparker",
            "Pedro",
            "Parker",
            "2/2/1975",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtz0trx_Uc5b-mdWX7B0TiLsDznTjMFRBPIg&usqp=CAU",
            "67890",
            "Chofer",
            "5491161878832"))
    }

    fun getPrestadores () : MutableList<Prestador>{
        return listaPrestadores
    }

}