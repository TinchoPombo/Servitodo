package com.ort.servitodo.repositories

import com.ort.servitodo.entities.Publicacion

class PublicacionRepository {

    private var listaPublicaciones : MutableList<Publicacion> = mutableListOf()

    init{
        listaPublicaciones.add(Publicacion(1,1,"https://resizer.glanacion.com/resizer/WW93VSwX4xU-ghvAwRFP9IqJOl4=/768x0/filters:format(webp):quality(80)/cloudfront-us-east-1.images.arcpublishing.com/lanacionar/FCDVQTKZZJH6HLYPS5D5ZBNTCY.jpg","Jorge","Roque", 1,"Plomero")    )
        listaPublicaciones.add(Publicacion(2,2,"https://i.bssl.es/miusyk/2012/05/Metallica-James-Hetfield.jpg","Pedro","Gomez",2,"Gasista")    )
        listaPublicaciones.add(Publicacion(3,3,"https://www.altonivel.com.mx/wp-content/uploads/2021/11/carlos-slim.jpg","Carlos","Sanchez",3,"Mecanico")    )
        listaPublicaciones.add(Publicacion(4,4,"https://static-cdn.jtvnw.net/jtv_user_pictures/574228be-01ef-4eab-bc0e-a4f6b68bedba-profile_image-300x300.png","Raul","Richi",4,"Pintor")    )
        listaPublicaciones.add(Publicacion(5,5,"https://cpad.ask.fm/846/535/880/910003015-1qn3b2f-71rh9nlgnba3l3a/original/file.jpg","Tomas","Muno",5,"Paseador de perros")    )
        listaPublicaciones.add(Publicacion(6,6,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtz0trx_Uc5b-mdWX7B0TiLsDznTjMFRBPIg&usqp=CAU","Pedro","Parker",6,"Chofer")    )
    }

    fun getPublicaciones () : MutableList<Publicacion>{
        return listaPublicaciones
    }
}