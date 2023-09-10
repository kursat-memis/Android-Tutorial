package com.kursatmemis.senddatatoactivityfromfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kursatmemis.senddatatoactivityfromfragment.databinding.FragmentOneBinding

class FragmentOne : Fragment() {

    private var binding: FragmentOneBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(inflater)

        binding!!.sendDataToActivity.setOnClickListener {

            val name = binding!!.nameEditText.text.toString()

            // Fragment'ın o anda ilişkili olduğu activity objesini return eder.
            val mainActivity = activity
            (mainActivity as MainActivity).setName(name)
        }

        return binding!!.root
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }

}