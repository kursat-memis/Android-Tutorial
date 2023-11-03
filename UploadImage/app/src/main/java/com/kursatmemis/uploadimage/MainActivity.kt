package com.kursatmemis.uploadimage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.kursatmemis.uploadimage.databinding.ActivityMainBinding

/**
 * Gerekli İzinler:
 * Aşağıdaki izinler AndroidManifest.xml dosyasında tanımlanmalıdır.

   Version'u 33'den küçük olan cihazlar için gerekli izin:
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

   Version'u 33'den büyük olan cihazlar için gerekli izin:
   <uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>

 * İznin nasıl istendiğine dair gerekli açıklamalar methodların başlarına yorum satırı olarak
 * eklendi.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerLauncher()
    }

    fun selectImage(view: View) {

        /**
         * Sürümü 33'den büyük olan ve küçük olan cihazlarda resim seçme işlemini gerçekleştirecek
         * kodlardaki tek fark;
         *
         * Sürümü 33'den büyük olan cihazlarda 'READ_MEDIA_IMAGES' iznini kullanırız,
         * Sürümü 33'den küçük olan cihazlarda 'READ_EXTERNAL_STORAGE' iznini kullanırız.
         *
         * Geri kalan tüm kodlar aynıdır.
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Sürümü 33'den büyük olan cihazlarda resim seç.
            selectImageOnDevicesWithVersionGreaterThan33()
        } else {
            // Sürümü 33'den küçük olan cihazlarda resim seç.
            selectImageOnDevicesWithVersionLessThan33()
        }
    }

    /**
     * ContextCompat.checkSelfPermission(context: Context, permission: String):
     * Bu method, 'permission' parametresinde belirtilen iznin kullanıcı tarafından verilip
     * verilmediğini kontrol eder. Bu method geriye bir Int değer return eder ve return ettiği
     * bu değer;
     * == PackageManager.PERMISSION_GRANTED -> İzin Verilmiş
     * == PackageManager.PERMISSION_DENIED -> İzin Reddedilmiş
     * demektir.
     *
     *
     * ActivityCompat.shouldShowRequestPermissionRationale(activity: Activity, permission: String):
     * Biz bu methodu kullanıcıya iznin neden gerekli olduğunu anlatacağımız durumlar için
     * kullanırız. Android, kullanıcıya iznin neden istendiğini anlatmamızı isterse bu method true
     * return edecektir. Ancak android, iznin neden istendiğini anlatmamızı istemiyorsa bu method
     * false dönecektir. Yani bu methodun true - false return etmesi bizim elimizde değil android'in
     * elinde. İzin sebebini anlatma gereksinimine android'in kendisi karar veriyor ve geriye
     * true ya da false return ediyor.
     *
     * Mesela aşağıdaki örnekte kullanım senaryomuz şu şekilde:
     * 1. ContextCompat.checkSelfPermission() methodu ile kullanıcının galeriye erişim için izin
     * verip vermediğini kontrol ediyoruz.
     * 2. Eğer kullanıcı izin vermişse, galerisine erişip fotoğraf seçtiriyoruz.
     * 3. Eğer kullanıcı izin vermemişse, ActivityCompat.shouldShowRequestPermissionRationale()
     * methodunu çağırarak kullanıcıya iznin neden gerektiğini açıklamamız gerekip gerekmediğini
     * android'e soruyoruz. Eğer bu method geriye true return ederse, bir Snackbar oluşturup izin
     * için gerekli açıklamaları gösterip kullanıcıdan izin istiyoruz. Eğer false return ederse
     * kullanıcıya Snackbar ile açıklama vs. göstermeden direkt izin istiyoruz.
     *
     *
     * Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI):
     * Intent.ACTION_PICK: Gerçekleştirilecek intent'in, kullanıcının cihazından bir öğe seçilme
     * işlemi olduğunu belirtir.
     * MediaStore.Images.Media.EXTERNAL_CONTENT_URI: Kullanıcının seçeceği öğe'nin galeri'den
     * bir öğe olacağını belirtir.
     */
    private fun selectImageOnDevicesWithVersionLessThan33() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Izin verilmemiş.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // Izin isterken kullanıcıya bir mantık göstermeliyiz. (İznin neden istendiğini açıkla)
                Snackbar.make(
                    binding.root,
                    "You have to give a permission to select an image from your gallery.",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Give Permission", View.OnClickListener {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            } else {
                // Izin isterken kullanıcıya bir mantık göstermemize gerek yok.
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            // Izin verilmiş.
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    // SDK Version numarası 33'den büyük olan cihazlar için bu method kullanılarak resim seçtirilir.
    private fun selectImageOnDevicesWithVersionGreaterThan33() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Izin verilmemiş.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                // Izin istersen kullanıcıya bir mantık göstermeliyiz. (İznin neden istendiğini açıkla)
                Snackbar.make(
                    binding.root,
                    "You have to give a permission to select an image from your gallery.",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Give Permission", View.OnClickListener {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }).show()
            } else {
                // Izin istersen kullanıcıya bir mantık göstermemize gerek yok.
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            // Izin verilmiş.
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    /**
     * Biz normalde intent'leri startActivity() methodu ile başlatırız. Ancak bazı durumlarda
     * intent gerçekleştikten sonra sonucu almak isteyebiliriz. Örneğin:
     * Intent kullanarak activity'ler arası geçiş yaptığımızda, sonuç bizim için önemsizdir.
     * Ancak Intent kullanarak kullanıcının galeriden bir resim seçmesini sağladığımızda seçilen
     * bu resmin URI bilgisini almamız gerekecektir. Ya da bir Intent ile kullanıcıdan izin
     * istediğimizde, kullanıcının izni verip vermediği sonucunu almamız gerekecektir.
     * Bunlar için startActivity() kullanamayız çünkü bu method ile intent gerçekleştirdiğimizde
     * sonucu yönetemiyoruz. İşte bu gibi durumlarda bir intenti başlattıktan sonra
     * sonucu yönetmek ve işlemek için eskiden startActivityForResult() methodu
     * kullanılırdı ancak android takımı bu methodu deprecated yaptı ve bu method yerine
     * 'ActivityResultLauncher' objelerinin kullanılmasını önerdi.
     *
     * ActivityResultLauncher<Intent>: Bir intent ile bir aktivite başlatmak ve sonucunu işlemek için.
     * ActivityResultLauncher<String>: Bir izin talebi için izin adını belirtmek için.
     *
     * registerForActivityResult(): Bu method bir ActivityResultLauncher objesi oluşturur.
     * Bu methodun sonuna yazılan lambda ifadesi ({result -> ...} kısmı) ile intent gerçekleştikten
     * sonra elde edilen sonucu yönetmek için kullanılır.
     *
     * "result.resultCode == RESULT_OK" Ifadesi: Eğer intent başarıyla gerçekleştirildi ise true
     * döner. Yani bizim senaryomuzda; kullanıcı başarılı bir şekilde galeriye gitti ve resim
     * seçtiyse true return eder.
     *
     */
    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageUri = intentFromResult.data
                    binding.imageView.setImageURI(imageUri)

                    // Eğer seçilen fotoğrafın bitmap'e dönüştürülmesi gerekirse;
                    convertToBitMap(imageUri)
                }
            }
        }

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                // Izin verilmiş.
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                // Izin verilmemiş.
                Toast.makeText(
                    this@MainActivity,
                    "You have to give a permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Eğer resim URI kullanılarak bitmap elde edilmek istenirse bu method çağrılır.
    private fun convertToBitMap(imageUri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            if (imageUri != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(contentResolver, imageUri)
                    bitmap = ImageDecoder.decodeBitmap(source)
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

}