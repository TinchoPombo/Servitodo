package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class PerfilPrestadorViewModel : ViewModel() {

    private lateinit var view : View

    private var usuario = Usuario()

    suspend fun getUsuario(id: String) : Usuario{
        return UsuarioRepository(view).getUsuarioById(id)
    }

    fun setView(v : View){
        this.view = v
    }

    fun obtenerUsuario() {
        viewModelScope.launch {
            usuario = getUsuario(UsuarioRepository(view).getIdSession())
        }
    }

    fun getUsuarioData(): Usuario{
        return usuario
    }










}