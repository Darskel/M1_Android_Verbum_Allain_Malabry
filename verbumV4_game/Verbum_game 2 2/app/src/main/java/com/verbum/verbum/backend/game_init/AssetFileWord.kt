package com.verbum.verbum.backend.game_init

import android.content.res.AssetManager
import com.verbum.verbum.backend.components.Word

//Classe qui permet de récupérer les mots dans les différents dictionnaires et de récupérer les mots de manière aléatoire.
class AssetFileWord(assetManager: AssetManager) : WordRepository {
    private val allWords =
        assetManager.open("dictionnaire.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    private val wordsForLevels =
        assetManager.open("devine.txt").readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()
    override fun find(word: Word): Boolean {
        return allWords.contains(word.word.uppercase().trim())
    }

    override fun random(): Word {
        return Word(allWords.random())
    }

    override fun getWordForLevel(currentLevelNumber: Long): Word {
        return Word(wordsForLevels[currentLevelNumber.toInt() - 1])
    }

    override val lastLevel: Long
        get() = 100
}