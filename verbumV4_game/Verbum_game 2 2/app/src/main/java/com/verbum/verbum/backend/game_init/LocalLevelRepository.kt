package com.verbum.verbum.backend.game_init

import android.content.SharedPreferences
import com.verbum.verbum.backend.components.Level
import kotlin.math.max

//Classe qui permet de récupérer le niveau d'où ce trouve le joueur et de modifier ce niveau
class LocalLevelRepository(
    private val sharedPreferences: SharedPreferences,
) : LevelRepository {
    private var lastLevel: Long
        get() {
            return sharedPreferences.getLong("LastLevel", 1)
        }
        set(value) {
            sharedPreferences.edit().putLong("LastLevel", value).commit()
        }

    override fun getCurrentLevelNumber(): Long {
        return lastLevel
    }

    override fun levelPassed(level: Level) {
        val settingLevel = max(level.number + 1, lastLevel)
        lastLevel = settingLevel
    }

    override fun reset() {
        lastLevel = 1
    }
}