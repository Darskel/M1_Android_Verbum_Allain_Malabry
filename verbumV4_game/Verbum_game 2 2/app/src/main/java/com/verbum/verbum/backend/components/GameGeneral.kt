package com.verbum.verbum.backend.components

//La classe Game est la classe qui permet de gérer toute la partie backend d'une partie
data class GameGeneral(val originalWord: Word, val guessWords: List<GuessWord>, val wordLength: Int = 5, private val keyboard: Keyboard = Keyboard.French(), ) {
    val availableKeyboard: Keyboard = keyboard.updateWithGuesses(guessWords)
    val isWon = guessWords.any { it.wordStatus == WordStatus.Correct }
    val isOver = guessWords.size == 6 && !isWon
}

//Fonction qui permet de mettre à jour le status du mot
private fun Keyboard.updateWithGuesses(guessWords: List<GuessWord>): Keyboard {
    val allWords: Map<Char, List<Pair<Char, EqualityStatus?>>> =
        guessWords.flatMap { guess ->
            guess.word.word.mapIndexed { index, character ->
                when (guess.wordStatus) {
                    WordStatus.Correct -> Pair(character, EqualityStatus.Correct)
                    is WordStatus.Incorrect -> Pair(character,
                        guess.wordStatus.equalityStatuses[index])
                    WordStatus.NotExists -> Pair(character, null)
                }
            }
        }.toSet().groupBy { it.first.uppercaseChar() }

    val keys = keys.map {

        val states = allWords[it.button.uppercaseChar()]
        val equalityStatus = when {
            states?.any { it.second == EqualityStatus.Correct } == true -> EqualityStatus.Correct
            states?.any { it.second == EqualityStatus.WrongPosition } == true -> EqualityStatus.WrongPosition
            states?.all { it.second == EqualityStatus.Incorrect } == true -> EqualityStatus.Incorrect
            else -> null
        }
        it.copy(equalityStatus = equalityStatus)
    }

    return withUpdatedButton(keys)
}

fun GameGeneral.copyWithNewGuess(guessWord: GuessWord, ) = copy(guessWords = guessWords + guessWord)