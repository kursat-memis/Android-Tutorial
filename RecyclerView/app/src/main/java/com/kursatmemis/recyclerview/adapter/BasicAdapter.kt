package com.kursatmemis.recyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kursatmemis.recyclerview.R
import com.kursatmemis.recyclerview.model.Student


/**
 * Adapter Nasıl Yazılır?
 * 1. Adapter class'ımızı RecyclerView.Adapter class'ından inherit ederiz.
 *    RecyclerView.Adapter class'ı bizden generic bir tip olarak RecyclerView.ViewHolder class'ını
 *    inherit eden bir class beklemektedir. Bunun için bir ViewHolder class'ı yazarız. ViewHolder
 *    class'ını istediğimiz yerde yazabiliriz ancak genel olarak Adapter class'ımızın içinde
 *    yazılır.
 *    RecyclerView.ViewHolder class'ının constructor'ı ise bizden bir View objesi beklemektedir.
 *    Bu View objesi, RecyclerView'ın item'ları için hazırlanmış olan layout dosyasının inflate
 *    edilerek dönüştürüleceği Kotlin objesini temsil etmektedir. Burada beklenen parametreye,
 *    MyViewHolder'ın constructor'ında aldığımız View objesini argüman olarak gönderebiliriz.
 *
 * 2. Inherit işlemini tamamladıktan sonra, bizden abstract olan function'ları implement etmemiz
 *    beklenir. Bu function'ları implement ederiz.
 */

var count = 0

class BasicAdapter(
    val studentList: ArrayList<Student>,
    val onItemClick: (student: Student) -> Unit,
) : RecyclerView.Adapter<BasicAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView
        private val ageTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.nameTextView)
            ageTextView = itemView.findViewById(R.id.ageTextView)

            itemView.setOnClickListener {
                onItemClick(studentList[adapterPosition])
            }
        }

        // Kodu daha temiz tutmak için veri atama işlemini bu function ile yapıyoruz.
        fun bind(student: Student) {
            Log.e(
                "mKm - krst",
                "onBindViewHolder: sunu kullaniyor: ${nameTextView.text.toString()}, sunu yazacak: ${student.name}"
            )
            nameTextView.text = student.name
            ageTextView.text = student.age.toString()

        }

    }

    /**
     * ViewHolder objelerinin oluşturulması ve return edilmesini sağlar. ViewHolder objesi
     * oluşturmak için, item'ları temsil eden layout dosyalarımızı inflation işlemine sokarak
     * onların Kotlin koduna, yani View objesine çevrilmelerini sağlarız.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.w("mKm - krst", "onCreateViewHolder: ${++count}")
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_student, parent, false)
        return MyViewHolder(itemView)
    }

    /**
     * ViewHolder'ı kullanarak data'ların view'lara bind edilmesini sağlar. Ancak kodumuzun daha
     * temiz olması için view'lara data atama işlemini bu function içinde yapmak yerine, ViewHolder
     * class'ı içinde oluşturduğumuz bind() function'ını kullanmayı tercih ederiz.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val student = studentList[position]
        holder.bind(student)
    }

    // RecyclerView'daki item sayısını return eder.
    override fun getItemCount(): Int {
        return studentList.size
    }

}