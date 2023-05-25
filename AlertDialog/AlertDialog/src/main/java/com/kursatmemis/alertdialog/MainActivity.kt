package com.kursatmemis.alertdialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight

/**
 * Genellikle bir olay (event) gerçekleştiğinde kullanıcıya bir bilgi veya seçenekler sunmak için
kullanılır. Örneğin; ‘İşlemi kabul ediyor musun?’ penceresi.
 * XML dosyası kullanılarak oluşturulmazlar. Dinamik olarak kotlin-java koduyla oluşturulurlar.
 * Alert Dialog, bir pop-up iletişim kutusu şeklinde görüntülenen User Interface elemanıdır.
 * Pop-up: User Interface’inde bir işlem gerçekleştiğinde belirli bir süre boyunca ekranın üstünde
görünen geçici bir bildirim penceresidir.

 * There are three core components that build an Alert Dialog.
1. Title Text
2. Message Text
3. Buttons - There are three types of buttons: Positive, Negative, and Neutral
(En fazla 3 buton olabilir.)
 * To create an AlertDialog we use the AlertDialog.Builder inner class.
 * Some of the methods:
- setCustomTitle - AlertDialog'un title'ına kendi view'ını eklemeni sağlar.
- setPositiveButton - AlertDialog'un positif butonunu set etmemizi sağlar.
- setNegativeButton - AlertDialog'un negatif butonunu set etmemizi sağlar.
- setNeutralButton - AlertDialog'un neutral butonunu set etmemizi sağlar.
- setView - AlertDialog'a özel bir görünüm eklememizi sağlar.
- show() - AlertDialog'un ekranda görüntülenmesini sağlar.
- setCancelable(true-false) - false geçilirse, AlertDialog'un kapanması için butonlardan birine
tıklanması gerekir. Eğer true geçilirse (default olarak böyle), back tuşu veya ekranda boş
bir yere tıklayarak AlertDialog kapatılabilir.
- setIcon(): Dialog penceresinde bir icon gözükmesini sağlar.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var showBasicAlertDialogButton: Button
    private lateinit var showAlertDialogWithDifferentButtonAndIconButton: Button
    private lateinit var showCustomAlertDialogButton: Button
    private lateinit var showAlertDialogWithItemsButton: Button
    private lateinit var showAlertDialogWithStyleButton: Button
    private lateinit var showAlertDialogWithCustomStyleButton: Button
    private lateinit var showAlertDialogWithButtonsCenteredButton: Button

    val positiveButton = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            "Clicked Positive Button", Toast.LENGTH_SHORT
        ).show()
    }
    val negativeButton = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            "Clicked Negative Button", Toast.LENGTH_SHORT
        ).show()
    }
    val neutralButton = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            "Clicked Neutral Button", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtons()

        // Basit bir AlertDialog.
        showBasicAlertDialogButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            /*builder.setTitle("Are you sure?")
            builder.setMessage("Are you sure that you want to delete all messages?")
            builder.setPositiveButton("Yes", positiveButton)
            builder.setNegativeButton("No", negativeButton)
            builder.setNeutralButton("Remind me later!", neutralButton)
            builder.show()*/
            builder.setTitle(R.string.title)
            builder.setMessage(R.string.message)
            builder.setPositiveButton(R.string.positive_button, positiveButton)
            builder.setNegativeButton(R.string.negative_button, negativeButton)
            builder.setNeutralButton(R.string.neutral_button, neutralButton)

            // builder.show() // builder.show() yerine aşağıdaki kodda kullanılabilir.
            val alertDialog = builder.create()
            alertDialog.show()
        }

        // AlertDialog üzerindeki butonların rengi vs atanır ve AlertDialog'a bir icon eklenir.
        showAlertDialogWithDifferentButtonAndIconButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.title)
            builder.setMessage(R.string.message)
            builder.setPositiveButton(R.string.positive_button, positiveButton)
            builder.setNegativeButton(R.string.negative_button, negativeButton)
            builder.setNeutralButton(R.string.neutral_button, neutralButton)

            builder.setIcon(R.drawable.icon_celebration)
            val alertDialog = builder.create()

            alertDialog.show()

            /*
            - Alert Dialog objesi, builder.create() ile oluşturulmuştur. Ancak;
            - Alert Dialog üzerindeki butonlar, alertDialog.show() methodundan sonra oluş-
              maktadır.
            - Bundan dolayı, alert dialog üzerindeki butonların renk vb. attribute'leri
              show() methodu cagrildiktan sonra set edilmelidir.
            - Eğer show() methodundan önce bu attribute'leri set etmeye kalkarsak,
              butonlar henüz oluşmadığından dolayı, NullPointerException hatası alırız.
            */

            /*
            Not: Butonlara erişebilmek için builder.create() diyerek AlertDialog class
                 objesini elde edip onu kullanmalıyız. builder objesi üzerinden butonlara
                 erişemeyiz.
            * */
            val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setBackgroundColor(Color.BLACK)
            positiveButton.setTextColor(Color.WHITE)
            positiveButton.setPadding(0, 0, 20, 0)

            val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setBackgroundColor(Color.BLACK)
            negativeButton.setTextColor(Color.WHITE)
            negativeButton.setPadding(0, 0, 20, 0)

            val neutralButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL)
            neutralButton.setBackgroundColor(Color.BLACK)
            neutralButton.setTextColor(Color.WHITE)
            neutralButton.setPadding(0, 0, 20, 0)

        }

        // Kendi oluşturduğumuz title ve view'ları AlertDialog içinde görüntülenir.
        showCustomAlertDialogButton.setOnClickListener {
            val layoutCustomTitle = layoutInflater.inflate(R.layout.custom_alert_dialog_title, null)
            val layoutCustomView = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setView(layoutCustomView)
            builder.setCustomTitle(layoutCustomTitle)
            builder.setMessage("Custom Title and Custom View (Here is message field.)")
            builder.setPositiveButton(R.string.positive_button, positiveButton)
            builder.setNegativeButton(R.string.negative_button, negativeButton)
            builder.setNeutralButton(R.string.neutral_button, neutralButton)
            builder.show()
        }

        // AlertDialog içinde bir array'in görüntülenmesi.
        showAlertDialogWithItemsButton.setOnClickListener {
            val items = arrayOf(
                "Item - 1", "Item - 2", "Item - 3", "Item - 4",
                "Item - 5", "Item - 6", "Item - 7", "Item - 8",
                "Item - 9", "Item - 10", "Item - 11", "Item - 12",
                "Item - 13", "Item - 14", "Item - 15", "Item - 16"
            )
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.title)
            builder.setPositiveButton(R.string.positive_button, positiveButton)
            builder.setNegativeButton(R.string.negative_button, negativeButton)
            builder.setNeutralButton(R.string.neutral_button, neutralButton)

            // i parametresi, tıklanılan öğrenin indexini belirtir.
            builder.setItems(items, DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this@MainActivity, "Clicked ${items[i]}", Toast.LENGTH_SHORT)
                    .show()
            })

            builder.show()
        }

        // AlertDialog için bir tema belirlendi.
        showAlertDialogWithStyleButton.setOnClickListener {
            /*
                - Android'in default olarak sunduğu bir style dosyasını kullandık.
                - Daha fazla style dosyası için:
                 https://developer.android.com/reference/android/app/AlertDialog
            */
            val builder = AlertDialog.Builder(
                this@MainActivity,
                android.R.style.Theme_DeviceDefault_Dialog_Alert
            )
            builder.setTitle("Title")
            builder.setMessage("Message")
            builder.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            builder.setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            builder.show()
        }

        // Kendi oluşturduğumuz temayı AlertDialog'a set ettik. Ve buton renkleri vs set ettik.
        showAlertDialogWithCustomStyleButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity, R.style.custom_alert_dialog_theme)
            builder.setTitle(R.string.title)
            builder.setMessage(R.string.message)
            builder.setPositiveButton(R.string.positive_button, positiveButton)
            builder.setNegativeButton(R.string.negative_button, negativeButton)
            builder.setNeutralButton(R.string.neutral_button, neutralButton)

            val alertDialog = builder.create()
            alertDialog.show()

            val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setTextColor(Color.BLACK)
            positiveButton.setBackgroundColor(ContextCompat.getColor(this, R.color.my_color))

            val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setTextColor(Color.BLACK)
            negativeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.my_color))
        }

        // AlertDialog'un positive ve negative butonlarını horizontal olarak center'ladık.
        showAlertDialogWithButtonsCenteredButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.title)
            builder.setMessage(R.string.message)
            builder.setPositiveButton(R.string.positive_button, positiveButton)
            builder.setNegativeButton(R.string.negative_button, negativeButton)

            val alertDialog = builder.create()
            alertDialog.show()

            val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            val layoutParams = positiveButton.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 10f
            positiveButton.layoutParams = layoutParams
            negativeButton.layoutParams = layoutParams

        }
    }

    private fun setButtons() {
        showBasicAlertDialogButton = findViewById(R.id.showBasicAlertDialogButton)
        showAlertDialogWithDifferentButtonAndIconButton =
            findViewById(R.id.showAlertDialogWithDifferentButtonAndIconButton)
        showCustomAlertDialogButton = findViewById(R.id.showCustomAlertDialogButton)
        showAlertDialogWithItemsButton = findViewById(R.id.showAlertDialogWithItemsButton)
        showAlertDialogWithStyleButton = findViewById(R.id.showAlertDialogWithStyleButton)
        showAlertDialogWithCustomStyleButton =
            findViewById(R.id.showAlertDialogWithCustomStyleButton)
        showAlertDialogWithButtonsCenteredButton =
            findViewById(R.id.showAlertDialogWithButtonsCenteredButton)
    }
}