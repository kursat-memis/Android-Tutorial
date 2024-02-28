package com.kursatmemis.uploadimage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.io.ByteArrayOutputStream

/**
 * AndroidManifest.xml Permission:
 * AndroidManifest.xml dosyasında tanımlanan izinler, uygulamanın cihazın özelliklerine ve
 * işlevlerine erişebilmesini sağlar. Örneğin uygulama internet üzerinde bir işlem yapacaksa
 * internet izninin, uygulama kameraya erişecekse kamera izninin, uygulama galeriye erişecekse
 * galeri izninin manifestte verilmesi gerekir. Bu izinler manifestte tanımlanmadığı sürece
 * uygulama bu işlevlere erişemez. Manifest dosyasında izin vermek için <uses-permission> tagleri
 * kullanılır.
 *
 * İki türlü izin var:
 * 1. Normal Permission: Kullanıcıya açıkca bir uyarı göstermeye gerek kalmadan Manifest.xml'de
 *    tanımlamak yeterlidir. (İnternet izni)
 *
 * 2. Dangerous Permission: Bu izinler, kullanıcının özel verilerine veya cihaz kaynaklarına
 *    erişim sağlayan ve potansiyel olarak kullanıcının gizliliğini tehlikeye atabilen izinlerdir.
 *    Uygulama, bu izinleri kullanmak için kullanıcıdan açık bir onay istemek zorundadır.
 *    Eğer açıkca bir izin almazsak uygulama crash olabilir, google store'dan kaldırılabilir vb.
 *    (Kamera, konum, galeri ve mikrofon gibi izinler)
 */

/**
 * Gerekli İzinler:
 * Aşağıdaki izinler AndroidManifest.xml dosyasında tanımlanmalıdır.

 *  Version'u 33'den küçük olan cihazlar için gerekli izin:
 *  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

 *  Version'u 33'den büyük olan cihazlar için gerekli izin:
 *  <uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>

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

    fun selectImage() {

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
     * 1. Parametre (action): Intent'in ne iş yapacağını belirtir.
     * 2. Parametre (uri): Intent'in işleyeceği verilerin konumunu belirtir.
     *
     * Intent.ACTION_PICK: Gerçekleştirilecek intent'in, kullanıcının cihazından bir öğe seçilme
     * işlemi olduğunu belirtir. (Resim-dosya-doc vb.)
     *
     * MediaStore.Images.Media.EXTERNAL_CONTENT_URI: Kullanıcının seçeceği verilerin galeriden
     * olacağını belirtir. Burada biz galeriden resim seçtirmek için gerekli URI'yı verdik.
     * Eğer muzik seçecek olsaydı==>MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
     * Video seçecek olsaydı ==> MediaStore.Video.Media.EXTERNAL_CONTENT_URI URI'larını verirdik.
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
     * intent gerçekleştikten sonra sonucu almak isteyebiliriz.
     * Mesela Intent kullanarak kullanıcının galeriden bir resim seçmesini sağladığımızda seçilen
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
     * registerForActivityResult(contract, callback):
     * Bu method bir ActivityResultLauncher objesi oluşturur.
     *
     * contract: ActivityResultContract türünden bir nesne. Ne tür bir işlem başlatmak istediğimizi
     * ve o işlemin sonucunun nasıl temsil edileceğini belirtiriz.
     * ActivityResultContracts.StartActivityForResult() -> Bir intent'i başlatmak ve sonuç almak için kullanılır.
     * ActivityResultContracts.RequestPermission -> İzin istemek için kullanılır.
     * ActivityResultContracts.TakePicture() -> Kamera ile fotoğraf çekmek için kullanılır.
     * ActivityResultContracts.PickMultipleImages -> Birden fazla resim seçmek için kullanılır.
     *
     * callback: ActivityResultContract türünden bir nesne. Intent gerçekleştikten sonra yapılmasını
     * istediğimiz işlemleri tanımladığımız lambda ifadesi.
     *
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

    /**
     * Resmi görsel işlemeye tabi tutmak (boyutunu değiştirme, kırpma, filtre uygulama vb.)
     * istiyorsak bunu bir bitmap ile yaparız. Biz, db'ye kaydedilecek resmin boyutunu biraz
     * düşürmek istediğimizden dolayı resmi bu method ile bitmap'e çeviriyoruz.
     */
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

    /**
     * Bu method bitmap'e dönüştürülen resmin en-boy oranını koruyarak resmi küçültür.
     * Yani boyu ne kadar küçültüysek o oranda eni de küçültüyor. Veya eni ne kadar küçülttüysek
     * o oranda boyu da küçültüyor ve bu sayede resmin en-boy oranı değişmiyor.
     *
     * image: Resmin bitmap temsili.
     * maximumSize: Yeni oluşacak resmin en veya yüksekliğinin maksimum alacağı değer.
     *              Buna 300 verilmesi tavsiye edilir. Bu demek oluyor ki resmi küçülttüğümüzde
     *              bu resmin eni boyundan büyükse, eni 300 olacak; yüksekliği eninden büyükse
     *              yüksekliği 300 olacak.
     */
    fun makeSmallerBitmap(image: Bitmap, maximumSize : Int) : Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()
        if (bitmapRatio > 1) {
            // landscape
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else {
            // portrait
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image,width,height,true)

    }


    /**
     * SQLite gibi veritabanlarına resim kaydedeceğimiz zaman bu resmi 'PNG-JPEG vb.' formatında
     * direkt kaydedemiyoruz. Bunun yerine bu resmi 'ByteArray' olarak kaydetmemiz gerekiyor.
     * Aşağıdaki method, bitmap'e dönüştürülmüş bir resmin ByteArray temsilini return eder ve bu
     * sayede bu resmi SQLite gibi veritabanlarına kaydedebiliriz.
     *
     * Not: Firebase'in Storage hizmetinde resimleri ByteArray'e dönüştürmemize gerek yoktur çünkü
     * storage'de resimleri 'PNG-JPEG vb.' formatta kaydedebiliyoruz.
     */
    fun fromBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
        val byteArray = outputStream.toByteArray()
        return byteArray
    }

    /**
     * ByteArray'i bitmap'e çevirir. Bu sayede veritabanına 'ByteArray' olarak kaydettiğimiz
     * resmi, veritabanından çektikten sonra Bitmap'e cast ederek bu resmi uygulamamızda
     * kullanabiliriz.
     */
    fun fromByteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }

}