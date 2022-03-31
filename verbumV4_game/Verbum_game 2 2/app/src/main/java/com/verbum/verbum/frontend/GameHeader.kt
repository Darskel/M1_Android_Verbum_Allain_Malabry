package com.verbum.verbum.frontend

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.verbum.verbum.R
import com.verbum.verbum.backend.components.Level

@OptIn(ExperimentalAnimationApi::class)
@Composable
//Fonction qui permet d'afficher les composant du Header
internal fun ColumnScope.GameHeader(level: Level, modifier: Modifier = Modifier) {
    GameHeader(modifier) { LevelHeaderContent(level) {} }
}

//Fonction qui configure la police d'écriture du header
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ColumnScope.GameHeader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier

        .align(Alignment.CenterHorizontally)) {

        Text(text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.align(
                Alignment.CenterHorizontally))
        content()

    }
}
//Fonction qui configure l'affichage des niveaux
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ColumnScope.LevelHeaderContent(
    level: Level,
    onRevealChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .align(
                Alignment.CenterHorizontally)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Niveau ${level.number}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.SansSerif,
        )

    }
}