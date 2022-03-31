package com.verbum.verbum

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.verbum.verbum.backend.game_init.AssetFileWord
import com.verbum.verbum.backend.game_init.LocalLevelRepository
import com.verbum.verbum.backend.gestionLevels.NextLevel
import com.verbum.verbum.backend.gestionLevels.GetWordStatus
import com.verbum.verbum.backend.gestionLevels.ResetLevels
import com.verbum.verbum.backend.modelview.LevelsViewModel
import com.verbum.verbum.frontend.GameHeader
import com.verbum.verbum.frontend.ScreenWord
import com.verbum.verbum.frontend.theme.verbumTheme

//Lancement de de l'activité de base
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            verbumTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // simple dependency injection
                    val assetWordRepository = remember {
                        AssetFileWord(assets)
                    }
                    val getWordStatus = remember {
                        GetWordStatus(assetWordRepository)
                    }

                    val sharedPreferences: SharedPreferences = remember {
                        getSharedPreferences("default", MODE_PRIVATE)
                    }
                    val levelRepository = remember {
                        LocalLevelRepository(sharedPreferences)
                    }

                    val getNextLevel = remember {
                        NextLevel(assetWordRepository, levelRepository)
                    }
                    val resetLevels = remember {
                        ResetLevels(levelRepository)
                    }
                    val levelViewModel = remember {
                        LevelsViewModel(levelRepository, getNextLevel, resetLevels)
                    }

                    val level = levelViewModel.state().collectAsState().value.currentLevel
                    if (level != null) {
                        ScreenWord(level, getWordStatus) {
                            levelViewModel.levelPassed()
                        }
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                GameHeader {

                                }
                                Text(text = "Vous avez réussi tous les niveaux.", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 32.dp))

                                Text(text = "Voulez vous recommencer au premier niveau ?", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 32.dp),
                                )
                                Button(
                                    onClick = {
                                        levelViewModel.reset()
                                    },
                                    modifier = Modifier.padding(top = 16.dp),
                                ) {
                                    Text(text = "Recommencer")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
