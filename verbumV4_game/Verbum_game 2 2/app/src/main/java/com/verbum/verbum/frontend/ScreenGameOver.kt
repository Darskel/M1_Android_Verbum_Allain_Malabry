package com.verbum.verbum.frontend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.verbum.verbum.backend.modelview.GameViewModel

@Composable
@OptIn(ExperimentalAnimationApi::class)
//Fonction qui affiche un message de défaite lorsque l'utilisateur à perdu le niveau
internal fun ScreenGameOver(
    state: GameViewModel.State,
    shownLost: () -> Unit,
) {

    AnimatedVisibility(state.gameGeneral.isOver, modifier = Modifier.fillMaxSize(),
        enter = fadeIn(), exit = fadeOut()
    ) {

        Box(Modifier
            .fillMaxSize()
            .background(Color(0x7F000000))
            .clickable {
                shownLost()
            }
            .padding(16.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.error),
            Alignment.Center) {
            Text(text = "Vous avez perdu. Tapottez pour recommencer.",
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.padding(32.dp),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black)
        }
    }
}