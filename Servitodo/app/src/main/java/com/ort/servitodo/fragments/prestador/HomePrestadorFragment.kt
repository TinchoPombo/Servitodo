package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentHomePrestadorBinding
import com.ort.servitodo.viewmodels.prestador.HomePrestadorViewModel

class HomePrestadorFragment : Fragment() {

    companion object {
        fun newInstance() = HomePrestadorFragment()
    }

    private lateinit var viewModel: HomePrestadorViewModel
    lateinit var v : View
    lateinit var btnCrearPublicacion : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home_prestador, container, false)

        btnCrearPublicacion = v.findViewById(R.id.btnCrearPublicacion)




        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePrestadorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btnCrearPublicacion.setOnClickListener {
            val action = HomePrestadorFragmentDirections.actionHomePrestadorFragmentToCrearPublicacionFragment()
            v.findNavController().navigate(action)
        }
    }

}