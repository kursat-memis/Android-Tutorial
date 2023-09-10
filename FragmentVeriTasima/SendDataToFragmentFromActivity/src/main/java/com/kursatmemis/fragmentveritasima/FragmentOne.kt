package com.kursatmemis.fragmentveritasima

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kursatmemis.fragmentveritasima.databinding.FragmentOneBinding

class FragmentOne : Fragment() {

    private var binding: FragmentOneBinding? = null
    private var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(inflater)

        // Bundle ile
        /*val bundle = arguments
        val name = bundle?.getString("name")*/


        binding!!.nameDataTextView.text = "Data: $name"

        return binding!!.root
    }

    // Kendi oluşturdugumuz setName metohdu ile
    fun setName(name: String) {
        this.name = name
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }

}