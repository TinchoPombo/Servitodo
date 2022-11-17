package com.ort.servitodo.fragments.prestador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.servitodo.adapters.PedidosPrestadorAdapter
import com.ort.servitodo.databinding.FragmentHomePrestadorBinding
import com.ort.servitodo.viewmodels.prestador.HomePrestadorViewModel

class HomePrestadorFragment : Fragment() {

    private val homePrestadorViewModel: HomePrestadorViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentHomePrestadorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        homePrestadorViewModel.setView(v)

        return v

    }


    override fun onStart() {
        super.onStart()

        homePrestadorViewModel.setPedidosAprobados()

        homePrestadorViewModel.pedidos.observe(viewLifecycleOwner, Observer { pedidos ->
            var recPedidosAceptados = binding.recPedidosAceptados

            recPedidosAceptados.setHasFixedSize(true)

            recPedidosAceptados.layoutManager = LinearLayoutManager(context)

            recPedidosAceptados.adapter = PedidosPrestadorAdapter(pedidos)

        })

        binding.btnCrearPublicacion.setOnClickListener {
            val action =
                HomePrestadorFragmentDirections.actionHomePrestadorFragmentToCrearPublicacionFragment()
            v.findNavController().navigate(action)
        }

        homePrestadorViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxt3.text = result.toString()
        })

    }


}