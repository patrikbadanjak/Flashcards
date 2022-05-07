package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.fragments.mainactivity.cardlistitem.CardAdapter
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel

class DeckDisplayDialogFragment : DialogFragment() {

    private val deckViewModel: DecksViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_deck_display_dialog, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = CardAdapter()

        recyclerView.adapter = adapter

        adapter.setCards(deckViewModel.selectedDeck.value?.cards ?: emptyList())

        return rootView
    }
}