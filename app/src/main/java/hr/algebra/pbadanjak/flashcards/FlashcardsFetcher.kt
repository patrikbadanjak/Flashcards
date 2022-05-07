package hr.algebra.pbadanjak.flashcards

import android.content.ContentValues
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import hr.algebra.pbadanjak.flashcards.dao.FirebaseHelper
import hr.algebra.pbadanjak.flashcards.model.Deck
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import hr.algebra.pbadanjak.flashcards.framework.*

class FlashcardsFetcher(private val context: Context) {

    val auth = FirebaseAuth.getInstance()

    fun fetchItems() {
        GlobalScope.launch {
            val decks = FirebaseHelper.getUserCreatedDecks(auth)

            decks?.forEach {
                val values = ContentValues().apply {
                    put(Deck::deckId.name, it.deckId)
                    put(Deck::name.name, it.name)
                    put(Deck::isPublic.name, it.isPublic)
                    put(Deck::dateAdded.name, it.dateAdded.toString())
                    put(Deck::lastChanged.name, it.lastChanged.toString())
                    put(Deck::tags.name, it.tags.toString())
                    put(Deck::userId.name, it.userId)
                    put(Deck::cards.name, Gson().toJson(it.cards))
                }
                context.contentResolver.insert(FLASHCARDS_PROVIDER_CONTENT_URI, values)
            }
            context.sendBroadcast<FlashcardsReceiver>()
        }
    }
}