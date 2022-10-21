package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.databinding.FragmentHomeClienteBinding
import com.ort.servitodo.databinding.FragmentPedidosClienteBinding
import com.ort.servitodo.viewmodels.cliente.PedidosClienteViewModel

class PedidosClienteFragment : Fragment() {

    private val pedidosViewModel: PedidosClienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentPedidosClienteBinding

    /*companion object {
        fun newInstance() = PedidosClienteFragment()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PedidosClienteViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPedidosClienteBinding.inflate(inflater, container, false)

        v = binding.root

        pedidosViewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()

        //pedidosViewModel.emptyList()

        pedidosViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.pedidosCargandotxt.text = result.toString()
        })

        pedidosViewModel.recyclerView(binding.recPedidos)

    }



}