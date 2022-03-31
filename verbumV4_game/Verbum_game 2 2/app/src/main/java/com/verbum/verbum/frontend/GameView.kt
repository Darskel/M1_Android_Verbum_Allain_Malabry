package com.verbum.verbum.frontend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verbum.verbum.backend.components.EqualityStatus
import com.verbum.verbum.backend.components.WordStatus
import com.verbum.verbum.backend.modelview.GameViewModel
import com.verbum.verbum.frontend.theme.correctBackground
import com.verbum.verbum.frontend.theme.enteringBackground
import com.verbum.verbum.frontend.theme.incorrectBackground
import com.verbum.verbum.frontend.theme.wrongPositionBackground

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
//Fonction qui permet d'initialiser la grille du jeu
internal fun GameGrid(state: GameViewModel.State, modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {

        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(6) { row ->
                Row(Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), horizontalArrangement = spacedBy(4.dp)) {
                    repeat(5) { column ->
                        val character: Char?
                        val status: EqualityStatus?
                        if (row < state.gameGeneral.guessWords.size) {
                            val guess = state.gameGeneral.guessWords[row]
                            character = guess.word.word[column]
                            status = when (guess.wordStatus) {
                                WordStatus.Correct -> EqualityStatus.Correct
                                is WordStatus.Incorrect -> guess.wordStatus.equalityStatuses[column]
                                WordStatus.NotExists -> EqualityStatus.Incorrect
                            }
                        } else {
                            character =
                                if (row == state.gameGeneral.guessWords.size) state.currentlyEnteringWord?.getOrNull(
                                    column) else null
                            status = null
                        }


                        WordCharacterBox(character = character,
                            status = status,
                            modifier = Modifier.weight(1f))
                    }

                }
            }
        }
    }
}
//la fonctions permet de gérer l'affichage de caractère dans chacune des cases du tableau
@Composable
internal fun WordCharacterBox(character: Char?, status: EqualityStatus?, modifier: Modifier = Modifier, ) {

    val color = when (status) {
        EqualityStatus.WrongPosition -> MaterialTheme.colorScheme.wrongPositionBackground
        EqualityStatus.Correct -> MaterialTheme.colorScheme.correctBackground
        EqualityStatus.Incorrect -> MaterialTheme.colorScheme.incorrectBackground
        null -> MaterialTheme.colorScheme.enteringBackground
    }

    val textColor = when (status) {
        null -> MaterialTheme.colorScheme.onBackground
        else -> MaterialTheme.colorScheme.onPrimary
    }
    val borderModifier = if (status == null) Modifier.border(1.dp,
        MaterialTheme.colorScheme.incorrectBackground) else Modifier
    CharacterBox(borderModifier, color, character, textColor, modifier)
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun CharacterBox(borderModifier: Modifier, color: Color, character: Char?, textColor: Color, modifier: Modifier = Modifier, ) {
    var lastChar by remember { mutableStateOf<Char?>(null) }
    if (character != null) {
        lastChar = character
    }
    Box(
        modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(2.dp))
            .then(borderModifier)
            .background(animateColorAsState(targetValue = color).value),
        contentAlignment = Alignment.Center) {
        AnimatedVisibility(character != null) {
            Text(lastChar?.uppercase() ?: "",
                color = animateColorAsState(targetValue = textColor).value,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                ))
        }
    }
}
