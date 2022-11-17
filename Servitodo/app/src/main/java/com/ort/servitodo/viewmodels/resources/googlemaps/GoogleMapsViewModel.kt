package com.ort.servitodo.viewmodels.resources.googlemaps

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.SphericalUtil
import com.ort.servitodo.R
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.cliente.DetallePublicacionFragment
import com.ort.servitodo.fragments.cliente.DetallePublicacionFragmentArgs
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class GoogleMapsViewModel : ViewModel() , OnMapReadyCallback {

    private val ARGENTINA_LATLNG_BOUNDS = LatLngBounds(
        LatLng(-56.9, -78.95), LatLng(-22.1, -51.9))

    private lateinit var view : View
    private lateinit var currentUser : Usuario
    private lateinit var supportMapFragment : SupportMapFragment
    private lateinit var usuarioRepository : UsuarioRepository
    private lateinit var markerAdapter : MarkerInfoWindowAdapter

    //--> Mutable Live Data
    val cargando = MutableLiveData<String>()
    val kmFilter = MutableLiveData<Int>()

    //--> Lists
    var addressList : MutableList<Address> = mutableListOf()
    var usersList : MutableList<Usuario> = mutableListOf()
    var publicacionesList : MutableList<Publicacion> = mutableListOf()
    var calificacionesList : MutableList<Puntuacion> = mutableListOf()

    //------------------------------------------------------------------------------------
    private fun setView(v : View){
        this.view = v
        markerAdapter = MarkerInfoWindowAdapter(view.context)
    }

    private suspend fun initUsers(){
        val id = usuarioRepository.getIdSession()
        currentUser = usuarioRepository.getUsuarioById(id) //--> Init usuario actual (logueado)

        usersList = usuarioRepository.getPrestadores()
    }

    private suspend fun initCalificacionesList(){
        val list = CalificacionesRepository().getCalificaciones()
        this.calificacionesList = list.filter { c -> !c.calificoPrestador } as MutableList<Puntuacion>
    }

    private fun validateCurrentUserAddress() : Boolean{
        val address = getAddress(this.currentUser.ubicacion)
        return address.size > 0
    }

    //------------------------------------------------------------------------------------
    fun initMap(f : Fragment, v : View){
        viewModelScope.launch {
            setView(v)
            usuarioRepository = UsuarioRepository(v)
            initUsers()
            initCalificacionesList()
            publicacionesList = PublicacionRepository().getPublicaciones()
            if(kmFilter.value == null){
                createGoogleMaps(f, "0km")
            }
        }
    }

    //------------------------------------------------------------------------------------
    //--> Crea el google maps en el fragment pasado por params
    fun createGoogleMaps(f : Fragment, km : String){
        if(!validateCurrentUserAddress()){
            popUpInvalidAddress()
        }
        else if(km.isEmpty()){
            cargando.value = "Debes elegir una opcion"
        }
        else{
            this.kmFilter.value = getNumberFromDropdownItem(km)

            if(!f.isAdded) return;

            supportMapFragment = f.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            supportMapFragment.getMapAsync(this)
            cargando.value = "Cargando..."
        }
    }

    //--> Callback del getMapAsync
    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.setLatLngBoundsForCameraTarget(ARGENTINA_LATLNG_BOUNDS);
        googleMap.setMinZoomPreference(10F)

        googleMap.setInfoWindowAdapter(markerAdapter)
        googleMap.clear() //--> borra viejos marcadores

        val listOfMarker = ArrayList<Marker>()
        listOfMarker.add(getCurrentUserMarker(googleMap))

        for (u in usersList) {
            val dir = convertStringToLatLng(u.ubicacion)
            val distance = calculateKm(dir)
            val publicacion = getPublicacionFromUser(u)

            if (publicacion != null && distance <= kmFilter.value!!) {
                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .position(dir)
                        .title("${u.nombre} ${u.apellido} (${publicacion.rubro.nombre})")
                )

                val puntaje = this.getPuntaje(publicacion.idPrestador)

                marker!!.tag = ObjectTag(publicacion, puntaje, distance)

                marker.showInfoWindow()
                marker.hideInfoWindow()

                listOfMarker.add(marker)
            }
        }

        redirectToDetallePublicacion(googleMap)

        showAllMarkers(googleMap, listOfMarker)

        if(listOfMarker.size == 1 && this.kmFilter.value!! >= 1){
            cargando.value = "No hay prestadores a ${this.kmFilter.value}km"
        }
    }


    //--> Redireccionamiento a Google Maps con view
    fun confirmRedirectionToMaps(goto : String, view : View){
        this.view = view
        MaterialAlertDialogBuilder(view.context)
            .setTitle("Deseas ser redireccionado a Google Maps?")
            .setNegativeButton("Cancelar") { dialog, which ->
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                viewModelScope.launch {
                    redirectToGoogleMaps(goto)
                }
            }
            .show()
    }

    fun redirectToGoogleMaps(goto : String){
        val gmmIntentUri = Uri.parse("https://www.google.co.in/maps/dir//${goto}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        view.context.startActivity(mapIntent)
    }

    //--------------------------------- FUNCIONES PRIVADAS -----------------------------------------------
    //--> Muestra los marcadores
    private fun showAllMarkers(mMap : GoogleMap, listOfMarker : ArrayList<Marker>){
        val b = LatLngBounds.Builder()
        for (m in listOfMarker) {
            b.include(m.position)
        }
        if(listOfMarker.size > 1){
            val cu = CameraUpdateFactory.newLatLngBounds(b.build(),100)  // 1° param: bounds, 2° param: paddingFromEdgeAsPX
            mMap.animateCamera(cu)
        }else{
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listOfMarker[0].position, 18.0f))
        }
    }

    //--> Convierte la direccion (string) a coordenadas
    private fun convertStringToLatLng(dir : String) : LatLng{
        addressList = this.getAddress(dir)
        return LatLng(addressList[0].latitude, addressList[0].longitude)
    }


    //--> Devuelve una lista con la direccion encontrada (el limite es 1 resultado)
    private fun getAddress(dir : String) : MutableList<Address>{
        val geocoder : Geocoder = Geocoder(view.context)
        return geocoder.getFromLocationName("${dir}, CABA", 1)
    }


    //--> Calcula la distancia en km entre dos puntos
    private fun calculateKm(dir: LatLng): Double {
        val currentUserDir = this.convertStringToLatLng(this.currentUser.ubicacion)
        return SphericalUtil.computeDistanceBetween(currentUserDir, dir) / 1000
    }


    //--> Obtiene el numero del valor del dropdown (index en 0 trae el num en string)
    private fun getNumberFromDropdownItem(value : String) : Int{
        val strs = value.split("km").toTypedArray()
        return strs[0].toInt()
    }

    //--> Obtiene el marker del usuario actual (logueado)
    private fun getCurrentUserMarker(mMap : GoogleMap) : Marker {
        val dir = convertStringToLatLng(this.currentUser.ubicacion)

        val icon: BitmapDescriptor by lazy {
            val color = ContextCompat.getColor(view.context, R.color.googlemaps_marker)
            BitmapHelper.vectorToBitmap(view.context, R.drawable.home, color)
        }
        val marker = mMap.addMarker(MarkerOptions().position(dir).title("Mi casa").icon(icon))
        marker!!.tag = marker
        marker.showInfoWindow()
        return marker
    }

    private fun getPublicacionFromUser(u : Usuario) : Publicacion?{
        return this.publicacionesList.find{ p -> p.idPrestador == u.id }
    }

    private fun redirectToDetallePublicacion(mMap : GoogleMap){
        mMap.setOnInfoWindowClickListener {
            val objectTag = it.tag as ObjectTag
            val bundle = bundleOf("receivePublicacion" to objectTag.publicacion)
            view.findNavController().navigate(R.id.detallePublicacionFragment, bundle)
        }
    }

    private fun getPuntaje(idPrestador : String) : Double{
        val result: Double
        val lista = calificacionesList.filter { c -> c.idPrestador ==  idPrestador }
        if(lista.isEmpty()){
            result = -1.0
        }
        else{
            var acum = 0.00
            for(l in lista){ acum += l.puntaje }
            result = acum / lista.size
        }
        return result
    }

    //----------------------------------- OTROS ---------------------------------------------
    private fun popUpInvalidAddress(){
        MaterialAlertDialogBuilder(view.context)
            .setTitle("Direccion invalida")
            .setMessage("Debes tener una direccion valida")
            .setPositiveButton("Ok") { dialog, which ->
            }
            .show()
    }
}