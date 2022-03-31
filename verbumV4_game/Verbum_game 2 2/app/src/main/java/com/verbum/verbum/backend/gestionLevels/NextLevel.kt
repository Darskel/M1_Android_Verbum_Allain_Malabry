package com.verbum.verbum.backend.gestionLevels

import com.verbum.verbum.backend.components.Level
import com.verbum.verbum.backend.game_init.LevelRepository
import com.verbum.verbum.backend.game_init.WordRepository

//Classe qui permet d'aller au prochain niveau
class NextLevel(
    private val wordRepository: WordRepository,
    private val levelRepository: LevelRepository,
) {
    fun execute(): Level? {
        val currentLevelNumber = levelRepository.getCurrentLevelNumber()
        if (currentLevelNumber >= wordRepository.lastLevel + 1) return null
        return wordRepository.getWordForLevel(currentLevelNumber).let { word ->
            Level(currentLevelNumber, word)
        }
    }
}