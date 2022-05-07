package hr.algebra.pbadanjak.flashcards.fragments.mainactivity.cardlistitem

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hr.algebra.pbadanjak.flashcards.model.Card

class CardAdapter : Adapter<ViewHolder>() {

    private var cards: List<Card> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TwoLineItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO propagate view click to set selectedDeck inside viewModel

        bind(cards[position], holder as TwoLineItemViewHolder)
    }

    override fun getItemCount() = cards.size

    private fun bind(card: Card, viewHolder: TwoLineItemViewHolder) {
        viewHolder.tvFirstLine.text = card.question
        viewHolder.tvSecondLine.text = card.answer
    }

    fun setCards(cards: List<Card>) {
        this.cards = cards
    }
}