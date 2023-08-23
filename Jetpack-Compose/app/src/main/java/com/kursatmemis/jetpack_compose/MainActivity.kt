package com.kursatmemis.jetpack_compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Modifier: Tüm component'lerde ortak bulunan attribute'leri
 * barındıran ortak bir yapı olması adına bunu koymuslar. Sebebi ise
 * her composoble fonksiyonun parametresine her componentte var olan
 * attribute'leri yazarak kendilerini tekrar etmektense ortak olanları
 * barındıran tek bir obje olarak bir parametre olusturmalarını.
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen3()
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen3() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var myString = remember { mutableStateOf("Android") }

        TextField(value = myString.value, onValueChange = {
            myString.value = it
            Log.w("mKm - compose", it)
        })
        
        Spacer(modifier = Modifier.padding(7.dp))
        
        Text(
            text = "Hello Android!",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.padding(7.dp))

        Button(
            onClick = { Log.w("mKm - compose", "Clicked") }
        ) {
            Text(text = "Click", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.padding(7.dp))

        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.mettalica),
            contentDescription = "Metallica"
        )

        Spacer(modifier = Modifier.padding(7.dp))

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )

        Spacer(modifier = Modifier.padding(7.dp))

        Image(
            painter = ColorPainter(Color.Red), null, modifier = Modifier.size(18.dp, 18.dp)
        )
    }
}

@Composable
fun MainScreen2() {
    Column(modifier = Modifier.fillMaxSize(1f)) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(1f)
                .background(Color.DarkGray),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(text = "Text - 1")
            CustomText(text = "Text - 2")
            CustomText(text = "Text - 3")

        }

        Column(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(text = "Text - 4")
            Spacer(modifier = Modifier.padding(3.dp))
            CustomText(text = "Text - 5")
            Spacer(modifier = Modifier.padding(3.dp))
            CustomText(text = "Text - 6")
        }

        Row(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(Color.Cyan),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomText(text = "Tes - One")
            CustomText(text = "Tes - Two")
            CustomText(text = "Tes - Three")
        }
    }
}

@Composable
fun MainScreen1() {

    // Componentleri alt alta koyar.
    Column {
        Text(
            text = "Hello JectPack-Compose",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 18.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .border(
                    1.dp, Color.Red, RoundedCornerShape(15.dp)
                )
                .padding(3.dp)
        )

        Text(
            text = "Hello From Me!",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 18.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .border(
                    1.dp, Color.Red, RoundedCornerShape(15.dp)
                )
                .padding(3.dp)
        )

        // Componentleri yan yana koyar.
        Row {
            Text(
                text = "Hello JectPack-Compose",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace,
            )

            Text(
                text = "Hello From Me!",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .border(
                        1.dp, Color.Red, RoundedCornerShape(15.dp)
                    )
                    .padding(3.dp)
            )
        }

        // Component'leri üst üste koyar.
        Box {
            Image(
                painter = painterResource(id = R.drawable.mettalica),
                contentDescription = "Metallica",
                modifier = Modifier
                    .height(180.dp)
                    .width(150.dp)
            )
            Text(
                text = "This is Metallica!",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Black)
            )
        }




        CustomText(text = "Custom-Text")

    }

}

// fillMaxSize: genişlik ve yükseklik bakımından doldurabildiğin
// kadar doldur. (Diğer compenent'lerin üstüne doldurma.)
// parametre olarak 0.5 verirsek, dolduracağı tüm alanın yarsını doldurur.

@Composable
fun CustomText(text: String) {
    Text(
        modifier = Modifier
            .background(Color.Black)
            .padding(8.dp)
            .clickable {
                Log.w("mKm - compose", "clicked")
            },

        text = text,
        fontSize = 18.sp,
        color = Color.White,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreen3()
}