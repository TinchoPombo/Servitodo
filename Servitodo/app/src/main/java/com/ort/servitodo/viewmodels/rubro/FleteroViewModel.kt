package com.ort.servitodo.viewmodels.rubro

import android.view.View
import androidx.lifecycle.ViewModel

class FleteroViewModel : ViewModel() {
    private lateinit var view : View

    fun setView(v : View){
        this.view = v
    }
}