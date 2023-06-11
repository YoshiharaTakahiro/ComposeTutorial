package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme{
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }
}

// メッセージのデータクラス
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg : Message){

    Row(modifier = Modifier.padding(all = 8.dp)){
        Image(painter = painterResource(id = R.drawable.rainbow),
            contentDescription = "Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape) // 画像を円形切り抜き
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // メッセージの展開状態を管理する変数
        var isExpanded by remember { mutableStateOf(false) }
        // メッセージの展開状態で背景色を切り替える
        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )

        // このColumnをクリックすると、isExpanded変数のOn/Offの切り替え。
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp,
                color = surfaceColor,
                // animateContentSizeは、Surfaceのサイズを徐々に変化させます。
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // メッセージが展開状態のときは全て表示、それ以外は１行のみ表示
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>){

    LazyColumn{
        items(messages){message ->
            MessageCard(msg = message)
        }
    }

}

// プレビューでレイアウトを確認するときは
// 引数のないコンポーズ関数にする必要がある
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard(){
    ComposeTutorialTheme {
        Surface {
            MessageCard(Message("Author","Body"))
        }
        
    }
}

@Preview
@Composable
fun PreviewConversation(){
    ComposeTutorialTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}
