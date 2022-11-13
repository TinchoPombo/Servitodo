package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ort.servitodo.databinding.FragmentPerfilPrestadorBinding
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.prestador.PerfilPrestadorFragmentDirections
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class PerfilPrestadorViewModel : ViewModel() {

    private lateinit var view : View


    suspend fun getUsuario(id: String) : Usuario{
        return UsuarioRepository(view).getUsuarioById(id)
    }

    fun setView(v : View){
        this.view = v
    }

    fun cargarPerfil(binding: FragmentPerfilPrestadorBinding) {
        viewModelScope.launch {
            val usuario = getUsuario(UsuarioRepository(view).getIdSession())

            binding.etCompleteName.text = "${usuario.nombre} ${usuario.apellido}"
            binding.etEmail.text = usuario.mail
            binding.etDireccion.text = usuario.ubicacion
            binding.etTelefono.text = usuario.telefono
            Glide
                .with(view)
                .load(usuario.foto)
                .into(binding.foto)
        }
    }

    fun logOut(){
        //            val action = PerfilPrestadorFragmentDirections.actionPerfilPrestadorFragmentToLoginActivity()
//            val request : NavDeepLinkRequest = fromUri(Uri.parse("com.ort.servitodo.fragments.login.LogInFragment")).build()
//            v.findNavController().navigate(request)

        /*
            val intent = Intent(context,MainActivity().javaClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        */
        UsuarioRepository(view).deleteSharedPreferences()

        val action = PerfilPrestadorFragmentDirections.actionPerfilPrestadorFragmentToMainActivity()
        view.findNavController().navigate(action)
    }
}