package com.kursatmemis.navigationcomponent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.kursatmemis.navigationcomponent.databinding.FragmentOneBinding
import com.kursatmemis.navigationcomponent.databinding.FragmentTwoBinding

class FragmentTwo : Fragment() {

    private lateinit var binding: FragmentTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTwoBinding.inflate(layoutInflater, container, false)

        val bundle: FragmentTwoArgs by navArgs()
        val age = bundle.age
        val name = bundle.name
        val numbers = bundle.numbers
        val myObj = bundle.myObj

        Log.w("mKm - navigation", "age: $age")
        Log.w("mKm - navigation", "name: $name")
        Log.w("mKm - navigation", "numbers: ${numbers[0]} ${numbers[1]} ${numbers[2]}")
        Log.w("mKm - navigation", "myObj: $myObj")

        binding.gotToFragmentThreeButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.switchToFragmentThree)
        }

        return binding.root
    }

}