package com.verbum.verbum.backend.modelview

import com.verbum.verbum.backend.components.GameGeneral
import com.verbum.verbum.backend.components.GuessWord
import com.verbum.verbum.backend.components.Word
import com.verbum.verbum.backend.components.WordStatus
import com.verbum.verbum.backend.gestionLevels.GetWordStatus

//Classe qui permet de gérer l'intéraction entre une entrée du clavier de l'utilisateur jusqu'à que le mot rentré par l'utilisateur atteigne la bonnee taille

class GameViewModel(
    private val initialGameGeneral: GameGeneral,
    private val getWordStatus: GetWordStatus,
) : BaseViewModel<GameViewModel.State>(State(initialGameGeneral)) {
    data class State(
        val gameGeneral: GameGeneral,
        val currentlyEnteringWord: String? = null,
        val doesNotExist: Boolean = false,
    )

    fun characterEntered(character: Char) {
        if (wordIsEnteredCompletely()) return
        val character = character.uppercaseChar()
        updateState {
            copy(currentlyEnteringWord = (currentlyEnteringWord ?: "") + character)
        }
    }

    private fun wordIsEnteredCompletely() =
        currentState().currentlyEnteringWord?.length == currentState().gameGeneral.wordLength

    fun backspacePressed() {
        updateState {
            val newWord = when {
                currentlyEnteringWord == null -> null
                currentlyEnteringWord.length == 1 -> null
                else -> currentlyEnteringWord.dropLast(1)
            }
            copy(currentlyEnteringWord = newWord)
        }
    }

    fun submit() {
        if (!wordIsEnteredCompletely()) return
        val word = Word(currentState().currentlyEnteringWord!!)
        val status = getWordStatus.execute(word,
            currentState().gameGeneral.originalWord)

        updateState {
            val newGuesses = if (status != WordStatus.NotExists) gameGeneral.guessWords + GuessWord(
                word, status
            ) else gameGeneral.guessWords
            copy(
                gameGeneral = gameGeneral.copy(guessWords = newGuesses),
                currentlyEnteringWord = if (status != WordStatus.NotExists) null else currentlyEnteringWord,
                doesNotExist = if (status == WordStatus.NotExists) true else doesNotExist)
        }
    }

    fun shownNotExists() {
        updateState { copy(doesNotExist = false) }
    }

    fun shownLost() {
        updateState {
            copy(gameGeneral = gameGeneral.copy(guessWords = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false)
        }
    }
}