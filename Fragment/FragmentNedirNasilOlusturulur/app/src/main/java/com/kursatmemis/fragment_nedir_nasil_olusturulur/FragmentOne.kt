package com.kursatmemis.fragment_nedir_nasil_olusturulur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = layoutInflater.inflate(R.layout.fragment_one, container, false)

        return fragmentView
    }

}