package hr.algebra.pbadanjak.flashcards.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class Deck(
    @Exclude
    val deckId: String = "",
    val userId: String = "",
    val name: String = "",
    val isPublic: Boolean = false,
    val dateAdded: Date = Date(),
    val lastChanged: Date = Date(),
    val tags: List<String> = listOf(),
    val cards: List<Card> = listOf()
) {
    override fun toString() = name
}
