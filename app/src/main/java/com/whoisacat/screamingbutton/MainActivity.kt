package com.whoisacat.screamingbutton

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whoisacat.screamingbutton.ui.theme.ScreamingButtonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreamingButtonTheme {
                // todo закончил на том что надо делать аутентификацию и клепать всю
                //  архитектуру экранов и т.д.
                ScreamingButtomList(SampleData.conversationSample)
            }
        }
    }
}

@Composable
fun ScreamingButtomList(buttons: List<ScreamingButton>) {
    LazyColumn {
        items(buttons.size) { number ->
            val button = buttons[number]
            key(button.postNumber) {
                ScreamingButtonCard(button)
            }
        }
    }
}

@Composable
fun ScreamingButtonCard(button: ScreamingButton) {
    var isAlarmed by rememberSaveable { mutableStateOf(button.isAlarmed) }
    val surfaceColor: Color by animateColorAsState(
        if (button.isAlarmed) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .background(color = surfaceColor)) {
        Image(
            painter = when (button.isAlarmed) {
                true -> painterResource(id = R.drawable.ic_alarma)
                false -> painterResource(id = R.drawable.ic_ok)
            },
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.clickable {
            //todo открыть другой фрагмент с подробной перепиской и/или другими событиями
            isAlarmed = !button.isAlarmed
        }) {
            Text(
                text = button.postNumber,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = button.specification,
                color = when (isAlarmed) {
                    true -> MaterialTheme.colors.error
                    false -> MaterialTheme.colors.primary },

                modifier = Modifier.padding(bottom = 4.dp),
                maxLines = if (isAlarmed) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(4.dp))

            if (!button.message.isNullOrEmpty()) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(all = 1.dp)
                        .background(color = surfaceColor)
                ) {
                    Text(
                        text = button.message,
                        modifier = Modifier.padding(bottom = 4.dp),
                        maxLines = if (isAlarmed) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewScreamingButton() {
    ScreamingButtonTheme {
        ScreamingButtonCard(
            ScreamingButton(
                "Палата 117",
                false,
                "Средняя койка. Мужчина в полном рассвете сил",
                "Меня уже пора выписывать"
            )
        )
    }
}

@Preview
@Composable
fun PreviewScreamingButtonList() {
    ScreamingButtomList(SampleData.conversationSample)
}