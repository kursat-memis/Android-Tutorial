package com.kursatmemis.navigationcomponent

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.kursatmemis.navigationcomponent.databinding.FragmentOneBinding
import java.io.Serializable

class FragmentOne : Fragment() {

    private lateinit var binding: FragmentOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(layoutInflater, container, false)

        binding.goToFragmentTwoButton.setOnClickListener {
            val directions =
                FragmentOneDirections.switchToFragmentTwo(
                    intArrayOf(1, 2, 3),
                    MyObj("Ali", "Bilmem"),
                    "Kursat",
                    24
                )
            // Navigation.findNavController(it).navigate(R.id.switchToFragmentTwo)
            Navigation.findNavController(it).navigate(directions)
        }

        return binding.root
    }

}

data class MyObj(val name: String, val surname: String) : Serializable