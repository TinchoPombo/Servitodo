package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.ViewModel

class PerfilClienteViewModel : ViewModel() {

    private lateinit var view : View

    fun setView(v : View){
        this.view = v
    }
}