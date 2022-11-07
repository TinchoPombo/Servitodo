package com.ort.servitodo.viewmodels.resources.googlemaps

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.SphericalUtil
import com.ort.servitodo.R
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class GoogleMapsViewModel : ViewModel() , OnMapReadyCallback {

    private lateinit var view : View
    private lateinit var currentUser : Usuario
    private lateinit var supportMapFragment : SupportMapFragment
    private lateinit var usuarioRepository : UsuarioRepository

    //--> Mutable Live Data
    val selectedDir = MutableLiveData<String>()
    val cargando = MutableLiveData<String>()
    val kmFilter = MutableLiveData<Int>()

    //--> Lists
    var addressList : MutableList<Address> = mutableListOf()
    var usersList : MutableList<Usuario> = mutableListOf()

    //------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
        this.usuarioRepository = UsuarioRepository(v)
    }

    fun initUsers(){
        viewModelScope.launch {
            val id = usuarioRepository.getIdSession()
            currentUser = usuarioRepository.getUsuarioById(id)

            val lista = if(currentUser.esPrestador){
                usuarioRepository.getClientes()
            } else{
                usuarioRepository.getPrestadores()
            }
            usersList = lista.filter { u -> u.id != currentUser.id } as MutableList<Usuario>
        }
    }

    fun getCurrentUserMarker(mMap : GoogleMap) : Marker?{
        val u = this.currentUser
        val dir = convertStringToLatLng("${u.ubicacion}, CABA")
        val marker = mMap.addMarker(MarkerOptions().position(dir).title("Mi casa").icon(
                BitmapDescriptorFactory.defaultMarker
                (BitmapDescriptorFactory.HUE_BLUE)))
        return marker
    }

    //------------------------------------------------------------------------------------
    //--> Crea el google maps en el fragment pasado por params
    fun createGoogleMaps(f : Fragment, km : Int){
        this.kmFilter.value = km
        cargando.value = "Cargando..."

        if(!f.isAdded) return;
        supportMapFragment = f.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    //--> Callback del getMapAsync
    override fun onMapReady(googleMap: GoogleMap) {

        viewModelScope.launch {
            val mMap = googleMap
            val listOfMarker = ArrayList<Marker>()

            mMap.clear() //--> borra viejos marcadores

            for(u in usersList){
                val dir = convertStringToLatLng("${u.ubicacion}, CABA")
                val distance = calculateKm(dir)
                if(distance <= kmFilter.value!!){
                    val dist = String.format("%.2f", distance / 1000)
                    val marker = mMap.addMarker(MarkerOptions().position(dir).title("${u.nombre} ${u.apellido} (${dist}km)"))
                    listOfMarker.add(marker!!)
                }
            }
            listOfMarker.add(getCurrentUserMarker(googleMap)!!)
            showAllMarkers(mMap, listOfMarker)
            cargando.value = ""
        }
    }

    //--> Redireccionamiento a Google Maps
    fun redirectToGoogleMaps(){
        val gmmIntentUri = Uri.parse("https://www.google.co.in/maps/dir/${currentUser.ubicacion}/${selectedDir.value}")
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
        val cu = CameraUpdateFactory.newLatLngBounds(b.build(),100)  // 1° param: bounds, 2° param: paddingFromEdgeAsPX
        mMap.animateCamera(cu)
    }


    //--> Convierte la direccion (string) a coordenadas
    private fun convertStringToLatLng(dir : String) : LatLng{
        var lat = 0.0
        var lng = 0.0

        addressList = this.getAddress(dir)
        lat = addressList[0].latitude
        lng = addressList[0].longitude
        return LatLng(lat, lng)
    }


    private fun getAddress(dir : String) : MutableList<Address>{
        val geocoder : Geocoder = Geocoder(view.context)
        return geocoder.getFromLocationName(dir, 1)
    }


    //--> Calcula la distancia en km entre dos puntos
    private fun calculateKm(dir : LatLng) : Double{
        val currentUserDir = this.convertStringToLatLng(this.currentUser.ubicacion)
        val result = SphericalUtil.computeDistanceBetween(currentUserDir, dir);
        return result / 1000
    }


    //--> Obtiene el numero del valor del radiobutton
    fun getNumberFromRadioButton(value : String) : Int{
        val strs = value.split("km").toTypedArray()
        return strs[0].toInt()
    }

    //----------------------------------- OTROS ---------------------------------------------
    //--> Esconde el keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    //Para llamar al hideKeyborad() --> view.hideKeyboard()
}