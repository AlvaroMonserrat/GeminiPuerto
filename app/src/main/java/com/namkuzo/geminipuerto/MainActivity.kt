package com.namkuzo.geminipuerto

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.namkuzo.geminipuerto.ui.component.LoadingErrorIndicator
import com.namkuzo.geminipuerto.ui.theme.GeminiPuertoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: GeminiViewModel = GeminiViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentLanguage = Locale.current.language
        setContent {
            GeminiPuertoTheme {
                // A surface container using the 'background' color from the theme
                GeminiPuertoScreen(
                    viewModel = viewModel,
                    onClick = {
                        viewModel.fetchDailyAdvice(currentLanguage)
                    }
                )
            }
        }
    }
}

@Composable
fun GeminiPuertoScreen(
    viewModel: GeminiViewModel,
    onClick: () -> Unit = {}
) {
    val dailyAdvice = viewModel.dailyAdvice.collectAsStateWithLifecycle()

    GeminiPuerto(
        dailyAdvice = if (dailyAdvice.value is ApiResponseState.Success) (dailyAdvice.value as ApiResponseState.Success<String>).data else "",
        onClick = onClick
    )

    LoadingErrorIndicator(
        state = dailyAdvice.value
    )
}

@Composable
fun GeminiPuerto(
    dailyAdvice: String,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(32.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.hello_description),
                style = MaterialTheme.typography.headlineMedium
            )
            AnimatedVisibility(visible = dailyAdvice.isNotBlank()) {
                Card(
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        text = dailyAdvice,
                        style = MaterialTheme.typography.titleLarge,
                        fontStyle = FontStyle.Normal
                    )
                }
            }

            CircularButtonWithImage(
                modifier = Modifier.padding(vertical = 8.dp),
                painter = painterResource(id = R.drawable.star_icon),
                onClick = {
                    Log.i("GEMINI", "Clicked!")
                    onClick()
                }
            )
            Text(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.click_button_advice)
            )
        }
    }
}

@Preview
@Composable
fun GeminiPuertoPreview() {
    GeminiPuerto(
        dailyAdvice = stringResource(id = R.string.example_advice)
    )
}

@Composable
fun CircularButtonWithImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick)
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painter,
            contentDescription = null
        )
    }
}
