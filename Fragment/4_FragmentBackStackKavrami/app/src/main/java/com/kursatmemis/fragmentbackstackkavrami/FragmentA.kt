package com.kursatmemis.fragmentbackstackkavrami

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

}