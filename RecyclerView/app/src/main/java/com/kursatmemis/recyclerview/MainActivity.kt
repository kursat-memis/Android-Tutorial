package com.kursatmemis.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.kursatmemis.recyclerview.adapter.AdapterWithViewBinding
import com.kursatmemis.recyclerview.adapter.BasicAdapter
import com.kursatmemis.recyclerview.databinding.ActivityMainBinding
import com.kursatmemis.recyclerview.factory.StudentListFactory
import com.kursatmemis.recyclerview.model.Student

/**
 * Nasıl Kullanılır?
 * 1. RecyclerView'ın gösterilmesini istediğimiz ekran için tasarlanan layout dosyasında
 *    RecyclerView bileşeni tanımlanır.
 *
 *    Not: Eğer ki ekranımızda sadece RecyclerView göstermek istiyorsak, Layout dosyamızın
 *    root element'ini FrameLayout yapmamız bize kolaylık sağlayacaktır. Ayrıca, root element'i
 *    RecyclerView yapmak bazen problemlere yol açabiliyor.
 *
 * 2. RecyclerView'ın item'larını temsil edecek olan layout dosyası hazırlanır. (item_student)
 * 3. RecyclerView'ın item'ları üzerinde gösterilecek olan data'ları modelleyecek bir data class
 *    oluşturulur.  (Student)
 * 4. Adapter class'ımız yazılır. (BasicAdapter)
 * 5. Son olarak RecyclerView için adapter ve layoutManager set edilir.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = BasicAdapter(StudentListFactory.createStudentList()) {
            Toast.makeText(this@MainActivity, "Clicked Item: $it", Toast.LENGTH_SHORT).show()
        }

        /*binding.recyclerView.adapter =
            AdapterWithViewBinding(StudentListFactory.createStudentList()) {
                val clickedStudent = it
                Toast.makeText(this@MainActivity, "You clicked: $clickedStudent", Toast.LENGTH_SHORT)
                    .show()
            }*/
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }
}


