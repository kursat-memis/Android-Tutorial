package com.kursatmemis.fragmenttransactionislemleri

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentB : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("mKm - FragmentB", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("mKm - FragmentB", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.e("mKm - FragmentB", "onCreateView")

        val view = layoutInflater.inflate(R.layout.fragment_b, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("mKm - FragmentA", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.e("mKm - FragmentA", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("mKm - FragmentA", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("mKm - FragmentB", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("mKm - FragmentB", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("mKm - FragmentB", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("mKm - FragmentB", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("mKm - FragmentB", "onDetach")
    }

}