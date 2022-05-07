package hr.algebra.pbadanjak.flashcards.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.algebra.pbadanjak.flashcards.model.Deck
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object FirebaseHelper {
    suspend fun getUserCreatedDecks(auth: FirebaseAuth): List<Deck>? {
        if (auth.currentUser == null) return null

        var decks: List<Deck> = listOf()

        GlobalScope.launch {
            FirebaseFirestore
                .getInstance()
                .collection("decks")
                .whereIn("userId", mutableListOf(auth.currentUser!!.uid))
                .get()
                .addOnCompleteListener{
                    decks = it.result!!.toObjects(Deck::class.java)
                }
        }.join()

        Log.i("FIREBASE_HELPER", "Decks: $decks")
        return decks
    }

}