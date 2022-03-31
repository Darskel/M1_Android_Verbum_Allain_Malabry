package com.verbum.verbum.backend.gestionLevels

import com.verbum.verbum.backend.game_init.LevelRepository

//Permet de recommencer un niveau
//La classe est utilisé quand tous les mots sont finis ou que le joueur se trompe sur un niveau
class ResetLevels(
    private val levelRepository: LevelRepository,
) {
    fun execute() {
        levelRepository.reset()
    }
}