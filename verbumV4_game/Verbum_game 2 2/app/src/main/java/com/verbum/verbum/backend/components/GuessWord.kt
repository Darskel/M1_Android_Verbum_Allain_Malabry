package com.verbum.verbum.backend.components

//Classe deviner qui lit un mot à son statut de découverte
data class GuessWord(
    val word: Word,
    val wordStatus: WordStatus
)