package hr.algebra.pbadanjak.flashcards.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import hr.algebra.pbadanjak.flashcards.model.Card
import hr.algebra.pbadanjak.flashcards.model.Deck
import kotlinx.coroutines.launch

class DecksViewModel : ViewModel() {

    var userDecks = MutableLiveData<List<Deck>>()
    var publicDecks = MutableLiveData<List<Deck>>()

    var selectedDeck = MutableLiveData<Deck>()

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val deckCollectionRef: CollectionReference = db.collection("decks")

    init {
        fetchUserDecks()
        fetchPublicDecks()
        listenForDeckChanges()
    }

    private fun fetchUserDecks() {
        deckCollectionRef
            .whereEqualTo(Deck::userId.name, auth.currentUser?.uid ?: -1)
            .get()
            .addOnCompleteListener {
                userDecks.value = it.result?.toObjects(Deck::class.java)
            }
    }

    private fun fetchPublicDecks() {
        deckCollectionRef
            .whereEqualTo("public", true)
            .whereNotEqualTo(Deck::userId.name, auth.currentUser?.uid ?: -1)
            .get()
            .addOnCompleteListener {
                publicDecks.postValue(it.result?.toObjects(Deck::class.java))
            }
    }

    private fun listenForDeckChanges() {
        deckCollectionRef
            .whereEqualTo(Deck::userId.name, auth.currentUser?.uid ?: -1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("DECK_VIEW_MODEL", "Listen failed:", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    userDecks.value = snapshot.toObjects(Deck::class.java)
                }
            }

        deckCollectionRef
            .whereEqualTo("public", true)
            .whereNotEqualTo(Deck::userId.name, auth.currentUser?.uid ?: -1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("DECK_VIEW_MODEL", "Listen failed:", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    publicDecks.postValue(snapshot.toObjects(Deck::class.java))
                }
            }
    }

    fun selectADeck(deck: Deck) {
        selectedDeck.value = deck
    }

    fun save(deck: Deck): Boolean {
        var isSuccessful = false

        deckCollectionRef
            .document(deck.deckId)
            .set(deck)
            .addOnCompleteListener { isSuccessful = it.isSuccessful }

        return isSuccessful
    }

    fun saveCardToDeck(card: Card, deck: Deck): Boolean {
        var isSuccessful = false

        deckCollectionRef
            .document(deck.deckId)
            .update(Deck::cards.name, FieldValue.arrayUnion(card))
            .addOnCompleteListener { isSuccessful = it.isSuccessful }

        return isSuccessful
    }

    fun delete(deck: Deck): Boolean {
        var isSuccessful = false

        deckCollectionRef
            .document(deck.deckId)
            .delete()
            .addOnCompleteListener { isSuccessful = it.isSuccessful }

        return isSuccessful
    }
}