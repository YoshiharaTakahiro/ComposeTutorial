package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("Android")
        }
    }
}

@Composable
fun MessageCard(name : String){
    Text("Hello $name World")
}

// プレビューでレイアウトを確認するときは
// 引数のないコンポーズ関数にする必要がある
@Preview
@Composable
fun PreviewMessageCard(){
    MessageCard("Android")
}
