package com.namkuzo.geminipuerto.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.namkuzo.geminipuerto.ApiResponseState

@Composable
fun LoadingErrorIndicator(
    state: ApiResponseState<*>?,
) {
    if (state is ApiResponseState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp)
                .pointerInput(Unit) {},
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .semantics { testTag = "loading-wheel" }
                    .size(48.dp),
                strokeWidth = 4.dp
            )
        }
    }

    if (state is ApiResponseState.Error) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp)
                .pointerInput(Unit) {},
            contentAlignment = Alignment.Center
        ) {
            Text(text = state.error.toString(), textAlign = TextAlign.Center)
        }
    }
}
