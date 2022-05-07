package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import hr.algebra.pbadanjak.flashcards.model.Deck

interface RecyclerViewDeckClickListener {
    fun onDeckClick(deck: Deck)
    fun onDeckLongClick(deck: Deck)
}