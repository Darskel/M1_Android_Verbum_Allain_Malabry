package com.verbum.verbum.backend.game_init

import com.verbum.verbum.backend.components.Word

interface WordRepository {
    val lastLevel: Long
    fun find(word: Word): Boolean
    fun random(): Word
    fun getWordForLevel(currentLevelNumber: Long): Word
}

