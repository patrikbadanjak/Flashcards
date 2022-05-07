package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.model.Deck

class DeckAdapter(private val context: Context,
                  private val deckClickListener: RecyclerViewDeckClickListener)
    : RecyclerView.Adapter<DeckAdapter.ViewHolder>() {

    private var decks: List<Deck> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDeckName: TextView = itemView.findViewById(R.id.tvDeckName)
        private val tvNumOfCards: TextView = itemView.findViewById(R.id.tvNumOfCards)
        private val cgTags: ChipGroup = itemView.findViewById(R.id.cgTags)

        fun bind(deck: Deck) {
            tvDeckName.text = deck.name
            tvNumOfCards.text = deck.cards.size.toString()
            if (cgTags.childCount != deck.tags.size) {
                deck.tags.forEach { tag ->
                    val tagView = Chip(itemView.context)
                    tagView.text = tag
                    cgTags.addView(tagView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val deckView = LayoutInflater.from(context).inflate(
            R.layout.deck,
            parent,
            false
        )
        return ViewHolder(deckView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.i("DECK_ADAPTER", "Deck clicked: $position")
            deckClickListener.onDeckClick(decks[position])
        }

        holder.itemView.setOnLongClickListener {
            deckClickListener.onDeckLongClick(decks[position])
            true
        }

        holder.bind(decks[position])
    }

    override fun getItemCount() = decks.size

    fun setItems(items: List<Deck>) {
        decks = items
    }
}