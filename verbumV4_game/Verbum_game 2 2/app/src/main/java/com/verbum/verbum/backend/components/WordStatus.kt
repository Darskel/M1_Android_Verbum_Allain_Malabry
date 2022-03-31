package com.verbum.verbum.backend.components

//WordStatus : classe qui permet de vérifier les lettres du mot rentrer par l'utilisateur selon 3 états
//WrongPosition: la lettre est dans le mot mais à la mauvaise place
//Correct: la lettre est à la bonne place
//Incorrect: la lettre n'est pas dans le mot de la solution
sealed class WordStatus {
    object NotExists : WordStatus()
    object Correct : WordStatus()
    data class Incorrect(
        val equalityStatuses: Array<EqualityStatus>
    ) : WordStatus() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Incorrect

            if (!equalityStatuses.contentEquals(other.equalityStatuses)) return false

            return true
        }

        override fun hashCode(): Int {
            return equalityStatuses.contentHashCode()
        }
    }
}

enum class EqualityStatus {
    WrongPosition,
    Correct,
    Incorrect;
}

