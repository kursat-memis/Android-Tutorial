package com.kursatmemis.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursatmemis.recyclerview.databinding.ItemStudentBinding
import com.kursatmemis.recyclerview.model.Student

/**
 * Adapter Nasıl Yazılır?
 * BasicAdapter class'ında genel olarak adapter'ın nasıl oluşturulacağı anlatıldı.
 *
 * ViewBinding kullanmak için tek fark şu:
 * MyViewHolder class'ında binding objesini parametre olarak alıyoruz ve RecyclerView.ViewHolder
 * constructor'ına binding.root'u argüman olarak gönderiyoruz.
 *
 * onCreateViewHolder() function'ında ise binding objesimizi oluşturuyor ve bunu kullanarak
 * ViewHolder instance'ı oluşturuyoruz.
 */

class AdapterWithViewBinding(
    private val studentList: ArrayList<Student>,
    val onItemClick: (student: Student) -> Unit
) : RecyclerView.Adapter<AdapterWithViewBinding.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            onItemClick(studentList[adapterPosition])
        }

        // Kodu daha temiz tutmak için veri atama işlemini bu function ile yapıyoruz.
        fun bind(student: Student) {
            binding.nameTextView.text = student.name
            binding.ageTextView.text = student.age.toString()
        }

    }

    /**
     * ViewHolder objelerinin oluşturulması ve return edilmesini sağlar. ViewHolder objesi
     * oluşturmak için, item'ları temsil eden layout dosyalarımız için bir binding objesi
     * oluşturuyoruz.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStudentBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
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