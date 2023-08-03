package com.kursatmemis.databinding

import android.view.View
import android.widget.Toast

class EventHandler {
    fun buttonOnClick(view: View) {
        Toast.makeText(view.context, "Clicked the button!", Toast.LENGTH_SHORT).show()
    }
}