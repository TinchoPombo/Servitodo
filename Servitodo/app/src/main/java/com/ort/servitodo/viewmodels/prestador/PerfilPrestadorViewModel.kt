package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.ViewModel

class PerfilPrestadorViewModel : ViewModel() {

    private lateinit var view : View

    fun setView(v : View){
        this.view = v
    }
}