package com.verbum.verbum.backend.gestionLevels

import com.verbum.verbum.backend.components.EqualityStatus
import com.verbum.verbum.backend.components.Word
import com.verbum.verbum.backend.components.WordStatus
import com.verbum.verbum.backend.game_init.WordRepository

//Classe qui permet de récupérer le status de chaque lettre d'un mot
class GetWordStatus(private val wordRepository: WordRepository) {
    fun execute(
        word: Word,
        original: Word,
    ): WordStatus {
        return when {
            !wordRepository.find(word) -> WordStatus.NotExists
            word == original -> WordStatus.Correct
            else -> {

                val missedCharacters = original.word.mapIndexed { index, char ->
                    if (word.word.getOrNull(index) != char) char else null
                }.filterNotNull().toMutableList()


                val status = word.word.mapIndexed { index, char ->
                    val charAtOriginal = original.word.getOrNull(index) ?: -1
                    val originalIndex = original.word.indexOf(char)
                    when {
                        char == charAtOriginal -> EqualityStatus.Correct
                        originalIndex >= 0 && missedCharacters.remove(char) -> EqualityStatus.WrongPosition
                        else -> EqualityStatus.Incorrect
                    }
                }

                WordStatus.Incorrect(status.toTypedArray())
            }
        }
    }
}