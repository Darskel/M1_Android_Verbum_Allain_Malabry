package com.verbum.verbum.frontend

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.verbum.verbum.backend.components.GameGeneral
import com.verbum.verbum.backend.components.Level
import com.verbum.verbum.backend.gestionLevels.GetWordStatus
import com.verbum.verbum.backend.modelview.GameViewModel

@Composable
//Fonction qui permet d'initialiser l'affichage de la partie
internal fun ScreenWord(
    level: Level,
    getWordStatus: GetWordStatus,
    levelCompleted: () -> Unit,
) {
    val word = level.word
    val viewModel = remember(word) {
        val initialGame = GameGeneral(word, listOf(
        ), 5)
        GameViewModel(initialGame, getWordStatus)
    }

    val state by viewModel.state().collectAsState()
    ScreenGame(level, state, onKey = {
        viewModel.characterEntered(it)
    },
        onBackspace = {
            viewModel.backspacePressed()
        },
        onSubmit = {
            viewModel.submit()
        }, shownError = {
            viewModel.shownNotExists()
        },
        shownWon = levelCompleted,
        shownLost = {
            viewModel.shownLost()
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
//Fonction qui affiche la grille et le clavier
fun ScreenGame(
    level: Level,
    state: GameViewModel.State,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    shownError: () -> Unit,
    shownWon: () -> Unit,
    shownLost: () -> Unit,
) {

    Box(Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 16.dp)) {

        Column(Modifier.padding(bottom = 8.dp)) {
            GameHeader(level)

            GameGrid(state,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
                    .fillMaxWidth(0.6f)
                    .align(CenterHorizontally))
            Spacer(modifier = Modifier.size(16.dp))
            GameKeyboard(
                state,
                onKey = onKey,
                onBackspace = onBackspace,
                onSubmit = onSubmit,
            )
        }
        ScreenError(state, shownError)
        ScreenWon(state, shownWon)
        ScreenGameOver(state, shownLost)
    }
}

