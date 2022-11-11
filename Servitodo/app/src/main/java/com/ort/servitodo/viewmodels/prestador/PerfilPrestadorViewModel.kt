package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ort.servitodo.databinding.FragmentPerfilPrestadorBinding
import com.ort.servitodo.entities.Usuario
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
}