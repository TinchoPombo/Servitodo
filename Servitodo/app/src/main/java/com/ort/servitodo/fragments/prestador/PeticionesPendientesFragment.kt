package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ort.servitodo.R
import com.ort.servitodo.adapters.PedidosAdapter
import com.ort.servitodo.databinding.FragmentHomeClienteBinding
import com.ort.servitodo.viewmodels.cliente.HomeClienteViewModel
import com.ort.servitodo.viewmodels.prestador.PeticionesPendientesViewModel
import com.ort.servitodo.databinding.FragmentPeticionesPendientesBinding

class PeticionesPendientesFragment : Fragment() {

    private val peticionesPendientesViewModel: PeticionesPendientesViewModel by viewModels()
    lateinit var v : View
    private lateinit var binding : FragmentPeticionesPendientesBinding




 /*   companion object {
        fun newInstance() = PeticionesPendientesFragment()
    }
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeticionesPendientesBinding.inflate(inflater, container, false)

        v = binding.root

        peticionesPendientesViewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()



        peticionesPendientesViewModel.emptyList()

        peticionesPendientesViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxt2.text = result.toString()
        })

        peticionesPendientesViewModel.recyclerView(binding.recPedidoPendiente)


    }


}