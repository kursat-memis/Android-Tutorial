package com.kursatmemis.fragmenttransactionislemleri

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class FragmentA : Fragment() {

    private var count = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.w("mKm - FragmentA", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("mKm - FragmentA", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.w("mKm - FragmentA", "onCreateView")

        val view = layoutInflater.inflate(R.layout.fragment_a, container, false)
        val text = view.findViewById<TextView>(R.id.countTextView)
        text.text = count.toString()
        val btn = view.findViewById<Button>(R.id.button7)
        btn.setOnClickListener {
            count++
            text.text = count.toString()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("mKm - FragmentA", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.w("mKm - FragmentA", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w("mKm - FragmentA", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.w("mKm - FragmentA", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w("mKm - FragmentA", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.w("mKm - FragmentA", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - FragmentA", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.w("mKm - FragmentA", "onDetach")
    }

}