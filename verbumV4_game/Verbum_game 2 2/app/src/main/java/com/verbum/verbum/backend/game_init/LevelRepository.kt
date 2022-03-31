package com.verbum.verbum.backend.game_init

import com.verbum.verbum.backend.components.Level

interface LevelRepository {
    fun getCurrentLevelNumber(): Long
    fun levelPassed(level: Level)
    fun reset()
}