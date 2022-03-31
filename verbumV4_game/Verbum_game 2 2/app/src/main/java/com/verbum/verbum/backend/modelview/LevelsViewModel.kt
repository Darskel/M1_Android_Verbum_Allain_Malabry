package com.verbum.verbum.backend.modelview

import com.verbum.verbum.backend.components.Level
import com.verbum.verbum.backend.game_init.LevelRepository
import com.verbum.verbum.backend.gestionLevels.NextLevel
import com.verbum.verbum.backend.gestionLevels.ResetLevels

//Classe qui permet de gérer la gestion d'un passage de niveau
class LevelsViewModel(
    private val levelRepository: LevelRepository,
    private val nextLevel: NextLevel,
    private val resetLevels: ResetLevels,
) : BaseViewModel<LevelsViewModel.State>(State()) {
    data class State(
        val currentLevel: Level? = null,
        val lastLevelReached: Boolean = false,

        )

    init {
        updateLevel()
    }

    fun levelPassed() {
        currentState().currentLevel?.let { levelRepository.levelPassed(it) }
        updateLevel()
    }

    private fun updateLevel() {
        val nextLevel = nextLevel.execute()
        if (nextLevel == null) {
            updateState { copy(lastLevelReached = true, currentLevel = null) }
            return
        }
        updateState {
            copy(currentLevel = nextLevel, lastLevelReached = false)
        }
    }

    fun reset() {
        resetLevels.execute()
        updateLevel()
    }
}