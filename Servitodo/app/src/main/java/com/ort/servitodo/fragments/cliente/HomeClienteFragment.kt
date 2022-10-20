package com.ort.servitodo.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.databinding.FragmentHomeClienteBinding
import com.ort.servitodo.viewmodels.cliente.HomeClienteViewModel

class HomeClienteFragment : Fragment() {

    private val homeClienteViewModel: HomeClienteViewModel by viewModels()
    lateinit var v : View
    private lateinit var binding : FragmentHomeClienteBinding

    /*
    companion object {
        fun newInstance() = HomeClienteFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeClienteViewModel::class.java)
        // TODO: Use the ViewModel
    }
    */

    //lateinit var publicacionAdapter : PublicacionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeClienteBinding.inflate(inflater, container, false)

        v = binding.root

        homeClienteViewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()

        homeClienteViewModel.emptyList()

        homeClienteViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxt.text = result.toString()
        })

        homeClienteViewModel.recyclerView(binding.recPublicacion)

    }

}