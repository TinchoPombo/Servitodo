package com.ort.servitodo.repositories


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.*
import kotlinx.coroutines.tasks.await

class PublicacionRepository {

    private var listaPublicaciones : MutableList<Publicacion> = arrayListOf()
    private var rubro = Rubro()

    val db = Firebase.firestore

    suspend fun getPublicaciones () : MutableList<Publicacion>{
        val questionRef = db.collection("publicaciones")
        try {
            val data = questionRef.get().await()
            for(document in data){
                listaPublicaciones.add(document.toObject())
            }
        } catch (e : Exception){ }
        return listaPublicaciones
    }

    suspend fun getRubro(idServicio : Int) : Rubro{
        val questionRef = db.collection("publicaciones").whereEqualTo("idServicio", idServicio)
        try {
            val data = questionRef.get().await()
            val document = data.first()
            val map = document["rubro"]!! as Map<String, Long>

            val id = map["id"]!!.toInt() //--> IDs: 1 Mantenimiento, 2 Fletero, 3 Paseaperros
            val nombre = map["nombre"]!! as String

            when (id) {
                1 -> rubro = Mantenimiento(map["precioConsulta"]!!.toInt(), id, nombre)
                2 -> rubro = Fletero(map["costoXHora"]!!.toInt(), map["pesoMax"]!!.toInt(), id,nombre)
                3 -> rubro = PaseaPerros(map["cantPerros"]!!.toInt(), map["precioPaseo"]!!.toInt(), id, nombre)
                else -> {
                    print(Log.d("ID Rubro incorrecto", "No existe el rubro ${nombre}"))
                }
            }
        } catch (e : Exception){ }
        return rubro
    }

    suspend fun getIdRubroPaseaPerros() : Int{
        var id = 0
        try {
            val lista = getPublicaciones().filter { p -> p.rubro.nombre == "PaseaPerros" }
            id = lista[0].rubro.id
        } catch (e : Exception) {
            Log.d("ERROR. PaseaPerros no encontrada", "No se encontraron publicaciones PASEAPERROS")
        }
        return id
    }

     suspend fun getPublicacionById(id : Int) : Publicacion{
        var publicacionEsperada = Publicacion()
         try {
             listaPublicaciones = getPublicaciones()
             publicacionEsperada = listaPublicaciones.find { p -> p.idServicio == id }!!
         } catch (e : Exception) {
             Log.d("ERROR. Publicacion no encontrada", "No se encontro la publicacion ${id}. ${e}")
         }
        return publicacionEsperada
    }

    suspend fun getPublicacionByIndex(index : Int) : Publicacion{

        var publicacionEsperada = Publicacion()

        try {
            listaPublicaciones = getPublicaciones()
            publicacionEsperada = listaPublicaciones.elementAt(index)
        } catch (e : Exception) { }

        return publicacionEsperada
    }

    suspend fun getSize(): Int {
        var cantidad : Int = 0

        try {
            listaPublicaciones = getPublicaciones()
            cantidad = listaPublicaciones.count()
        } catch (e : Exception) { }

        return cantidad

    }

    //----------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------------------------------------------------------------//
    /*init
    {
        listaPublicaciones.add(Publicacion(1,1,"https://resizer.glanacion.com/resizer/WW93VSwX4xU-ghvAwRFP9IqJOl4=/768x0/filters:format(webp):quality(80)/cloudfront-us-east-1.images.arcpublishing.com/lanacionar/FCDVQTKZZJH6HLYPS5D5ZBNTCY.jpg","Jorge","Roque", 1,"Plomero","HDSAKFHGLSDHF")    )
        listaPublicaciones.add(Publicacion(2,2,"https://i.bssl.es/miusyk/2012/05/Metallica-James-Hetfield.jpg","Andres","Gomez",2,"Gasista","HDSAKFHGLSDHF")    )
        listaPublicaciones.add(Publicacion(3,3,"https://www.altonivel.com.mx/wp-content/uploads/2021/11/carlos-slim.jpg","Carlos","Sanchez",3,"Mecanico","HDSAKFHGLSDHF")    )
        listaPublicaciones.add(Publicacion(4,4,"https://static-cdn.jtvnw.net/jtv_user_pictures/574228be-01ef-4eab-bc0e-a4f6b68bedba-profile_image-300x300.png","Raul","Richi",4,"Pintor","HDSAKFHGLSDHF")    )
        listaPublicaciones.add(Publicacion(5,5,"https://cpad.ask.fm/846/535/880/910003015-1qn3b2f-71rh9nlgnba3l3a/original/file.jpg","Tomas","Muno",5,"Paseador de perros","HDSAKFHGLSDHF")    )
        listaPublicaciones.add(Publicacion(6,6,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtz0trx_Uc5b-mdWX7B0TiLsDznTjMFRBPIg&usqp=CAU","Pedro","Parker",6,"Chofer","HDSAKFHGLSDHF")    )
    }*/
    /*init{
        listaPublicaciones.add(Publicacion(1,1,"https://resizer.glanacion.com/resizer/WW93VSwX4xU-ghvAwRFP9IqJOl4=/768x0/filters:format(webp):quality(80)/cloudfront-us-east-1.images.arcpublishing.com/lanacionar/FCDVQTKZZJH6HLYPS5D5ZBNTCY.jpg","Jorge","Roque", 1,"Plomero")    )
        listaPublicaciones.add(Publicacion(2,2,"https://i.bssl.es/miusyk/2012/05/Metallica-James-Hetfield.jpg","Andres","Gomez",2,"Gasista")    )
        listaPublicaciones.add(Publicacion(3,3,"https://www.altonivel.com.mx/wp-content/uploads/2021/11/carlos-slim.jpg","Carlos","Sanchez",3,"Mecanico")    )
        listaPublicaciones.add(Publicacion(4,4,"https://static-cdn.jtvnw.net/jtv_user_pictures/574228be-01ef-4eab-bc0e-a4f6b68bedba-profile_image-300x300.png","Raul","Richi",4,"Pintor")    )
        listaPublicaciones.add(Publicacion(5,5,"https://cpad.ask.fm/846/535/880/910003015-1qn3b2f-71rh9nlgnba3l3a/original/file.jpg","Tomas","Muno",5,"Paseador de perros")    )
        listaPublicaciones.add(Publicacion(6,6,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtz0trx_Uc5b-mdWX7B0TiLsDznTjMFRBPIg&usqp=CAU","Pedro","Parker",6,"Chofer")    )
    }*/

    /*
     val parent = Job()
     val scope = CoroutineScope(Dispatchers.Default + parent)
     scope.launch(){
         val publicaciones = getPublicaciones().toTypedArray()
         publicacionEsperada = publicaciones[id]
     }
    */

    /*
    fun getPublicaciones () : MutableList<Publicacion>{
        //traer lista de datos

        db.collection("publicaciones")
//             .whereEqualTo("tipo", "PERRO")
//             .limit(20)
//             .orderBy("edad")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (publicacion in snapshot) {
                        listaPublicaciones.add(publicacion.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        return listaPublicaciones
    }
     */
}