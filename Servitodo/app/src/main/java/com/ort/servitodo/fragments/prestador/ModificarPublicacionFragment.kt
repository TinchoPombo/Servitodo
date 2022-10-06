package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.prestador.ModificarPublicacionViewModel

class ModificarPublicacionFragment : Fragment() {

    companion object {
        fun newInstance() = ModificarPublicacionFragment()
    }

    private lateinit var viewModel: ModificarPublicacionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modificar_publicacion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ModificarPublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}