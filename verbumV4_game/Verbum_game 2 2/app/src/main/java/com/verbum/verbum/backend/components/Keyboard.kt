package com.verbum.verbum.backend.components

import androidx.annotation.Keep
import com.verbum.verbum.backend.components.Keyboard.Key.Companion.frenchKeys

//Les classes Language et KeyboardKeys ont été crées pour ajouter des claviers de différents types et language
//Ici avec les difficultés qu'on a eu on a laissé le clavier en français
enum class Language(val keys: List<Char>) {
    French(frenchKeys)
}

abstract class Keyboard(
    open val keys: List<Key>,
    val language: Language,
) {

    //Fonction pour mettre en place l'affichage des boutons
    abstract fun withUpdatedButton(keys: List<Key>): Keyboard
    data class Key(val button: Char, val equalityStatus: EqualityStatus?, ) {
        val enabled = equalityStatus != EqualityStatus.Incorrect

        @Keep
        companion object {
            val frenchKeys = listOf(
                'A',
                'Z',
                'E',
                'R',
                'T',
                'Y',
                'U',
                'I',
                'O',
                'P',
                'Q',
                'S',
                'D',
                'F',
                'G',
                'H',
                'J',
                'K',
                'L',
                'M',
                'W',
                'X',
                'C',
                'V',
                'B',
                'N')
        }
    }

    //Mise en place d'une classe langue pour mettre à jour le clavier selon la langue du téléphone
    data class French(override val keys: List<Key> = frenchKeys.map { Key(it, null) }.toList(), ) : Keyboard(keys, Language.French) {
        override fun withUpdatedButton(keys: List<Key>): Keyboard {
            return copy(keys = keys)
        }
    }
}