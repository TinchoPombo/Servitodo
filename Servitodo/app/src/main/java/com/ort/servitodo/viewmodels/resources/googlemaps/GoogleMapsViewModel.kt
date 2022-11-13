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
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.cliente.DetallePublicacionFragment
import com.ort.servitodo.fragments.cliente.DetallePublicacionFragmentArgs
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

    //------------------------------------------------------------------------------------
    private fun setView(v : View){
        this.view = v
        markerAdapter = MarkerInfoWindowAdapter(view.context)
    }

    private suspend fun initUsers(){
        val id = usuarioRepository.getIdSession()
        currentUser = usuarioRepository.getUsuarioById(id) //--> Init usuario actual (logueado)

        val lista = if(currentUser.esPrestador){
            usuarioRepository.getClientes()
        } else{
            usuarioRepository.getPrestadores()
        }
        usersList = lista.filter { u -> u.id != currentUser.id } as MutableList<Usuario> //--> Init lista
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
            this.kmFilter.value = getNumberFromRadioButton(km)

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
            val dir = convertStringToLatLng("${u.ubicacion}, CABA")
            val distance = calculateKm(dir)
            val publicacion = getPublicacionFromUser(u)
            if (publicacion != null && distance <= kmFilter.value!!) {
                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .position(dir)
                        .title("${u.nombre} ${u.apellido} (${publicacion.rubro.nombre})")
                        .snippet("${String.format("%.2f", distance)}km")
                )
                marker!!.tag = publicacion

                marker.showInfoWindow()
                marker.hideInfoWindow()

                listOfMarker.add(marker)

                redirectToDetallePublicacion(googleMap)
            }
        }

        showAllMarkers(googleMap, listOfMarker)

        if(listOfMarker.size == 1 && this.kmFilter.value!! >= 1){
            cargando.value = "No hay prestadores a ${this.kmFilter.value}km"
        }
    }

    //--> Redireccionamiento a Google Maps
    /*fun redirectToGoogleMaps(goto : String){
        val gmmIntentUri = Uri.parse("https://www.google.co.in/maps/dir/${currentUser.ubicacion}/${goto}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        view.context.startActivity(mapIntent)
    }*/

    //--> Redireccionamiento a Google Maps con view
    fun redirectToGoogleMaps(goto : String, v: View){
        val gmmIntentUri = Uri.parse("https://www.google.co.in/maps/dir//${goto}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        v.context.startActivity(mapIntent)
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
        return geocoder.getFromLocationName(dir, 1)
    }


    //--> Calcula la distancia en km entre dos puntos
    private fun calculateKm(dir: LatLng): Double {
        val currentUserDir = this.convertStringToLatLng(this.currentUser.ubicacion)
        return SphericalUtil.computeDistanceBetween(currentUserDir, dir) / 1000
    }


    //--> Obtiene el numero del valor del radiobutton (index en 0 trae el num en string)
    private fun getNumberFromRadioButton(value : String) : Int{
        val strs = value.split("km").toTypedArray()
        return strs[0].toInt()
    }

    //--> Obtiene el marker del usuario actual (logueado)
    private fun getCurrentUserMarker(mMap : GoogleMap) : Marker {
        val dir = convertStringToLatLng("${this.currentUser.ubicacion}, CABA")
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
            val publicacion = it.tag
            val bundle = bundleOf("receivePublicacion" to publicacion)
            view.findNavController().navigate(R.id.detallePublicacionFragment, bundle)
        }
    }

    //----------------------------------- OTROS ---------------------------------------------
    //--> Esconde el keyboard
    /*private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }*/
    //Para llamar al hideKeyborad() --> view.hideKeyboard()

    private fun popUpInvalidAddress(){
        MaterialAlertDialogBuilder(view.context)
            .setTitle("Direccion invalida")
            .setMessage("Debes tener una direccion valida")
            .setPositiveButton("Ok") { dialog, which ->
            }
            .show()
    }
}