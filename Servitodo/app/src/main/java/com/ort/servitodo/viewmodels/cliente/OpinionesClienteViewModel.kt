package com.ort.servitodo.viewmodels.cliente

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ort.servitodo.R

class OpinionesClienteViewModel : ViewModel() {

    /*private lateinit var view : View

    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }*/

    //----------------------------------------------------------------------------------------
    fun opinionesDelPrestador(view : View){

        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_opiniones_cliente)

        val recycler = dialog.findViewById<RecyclerView>(R.id.opinionesClienteRecycler)!!

        dialog.show()
    }

}