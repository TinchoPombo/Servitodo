package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel

class CrearPublicacionFragment : Fragment() {

    companion object {
        fun newInstance() = CrearPublicacionFragment()
    }

    private lateinit var viewModel: CrearPublicacionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crear_publicacion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearPublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}