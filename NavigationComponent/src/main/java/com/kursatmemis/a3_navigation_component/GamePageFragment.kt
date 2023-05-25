package com.kursatmemis.a3_navigation_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class GamePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_game_page, container, false)

        layout.findViewById<Button>(R.id.finishButton).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.gamePageToResultPage)
        }

        return layout
    }


}