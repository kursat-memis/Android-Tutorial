package com.kursatmemis.a4_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kursatmemis.a4_compose.ui.theme.BasicConceptsTheme
import com.kursatmemis.a4_compose.R


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicConceptsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Text(
            text = "Text - 1",
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "Image",
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp)
        )

        Text(
            text = "Text - 2",
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicConceptsTheme {
        Greeting("Android")
    }
}