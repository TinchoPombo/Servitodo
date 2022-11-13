package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ort.servitodo.databinding.FragmentPerfilClienteBinding
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.cliente.PerfilClienteFragmentDirections
import com.ort.servitodo.fragments.prestador.PerfilPrestadorFragmentDirections
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class PerfilClienteViewModel : ViewModel() {

    private lateinit var view : View


    suspend fun getUsuario(id: String) : Usuario {
        return UsuarioRepository(view).getUsuarioById(id)
    }

    fun setView(v : View){
        this.view = v
    }

    fun cargarPerfil(binding: FragmentPerfilClienteBinding) {
        viewModelScope.launch {
            val usuario = getUsuario(UsuarioRepository(view).getIdSession())

            binding.etName.text = usuario.nombre
            binding.etApellido.text = usuario.apellido
            binding.etEmail.text = usuario.mail
            binding.etDireccion.text = usuario.ubicacion
            binding.etTelefono.text = usuario.telefono
            Glide
                .with(view)
                .load(usuario.foto)
                .into(binding.foto)
        }
    }

    fun logOut() {
        UsuarioRepository(view).deleteSharedPreferences()

        val action = PerfilClienteFragmentDirections.actionPerfilClienteFragmentToMainActivity2()
        view.findNavController().navigate(action)
    }
}