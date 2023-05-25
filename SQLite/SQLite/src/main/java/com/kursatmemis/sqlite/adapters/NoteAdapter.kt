package com.kursatmemis.sqlite.adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kursatmemis.sqlite.R
import com.kursatmemis.sqlite.models.Note

class NoteAdapter (val context: AppCompatActivity, val arr: MutableList<Note>) : ArrayAdapter<Note>(context, R.layout.custom_list_view, arr) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater
        val customView = layoutInflater.inflate(R.layout.custom_list_view, null)

        val id = customView.findViewById<TextView>(R.id.id)
        val note = customView.findViewById<TextView>(R.id.note)
        val date = customView.findViewById<TextView>(R.id.tarih)

        id.text = arr.get(position).id.toString()
        note.text = arr.get(position).note
        date.text = arr.get(position).date

        return customView
    }

}