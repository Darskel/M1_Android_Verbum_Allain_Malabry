package com.verbum.verbum.frontend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.verbum.verbum.backend.modelview.GameViewModel
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalAnimationApi::class)

//Fonction qui permet d'afficher un message lorsque l'utilisateur n'a pas rentrer de mot existant
internal fun BoxScope.ScreenError(
    state: GameViewModel.State,
    shownError: () -> Unit,
) {
    LaunchedEffect(key1 = state.doesNotExist, block = {
        if (state.doesNotExist) {
            delay(2000)
            shownError()
        }
    })
    AnimatedVisibility(state.doesNotExist, modifier = Modifier
        .align(Alignment.BottomCenter)) {
        Box(Modifier
            .align(Alignment.BottomCenter)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.error)
            .padding(16.dp)) {
            Text(text = "Le mot n'existe pas.",
                color = MaterialTheme.colorScheme.onError)
        }
    }
}