package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.model.Deck
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel

class MyDecksFragment : Fragment(), RecyclerViewDeckClickListener {

    private val decksViewModel: DecksViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeckAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_my_decks, container, false)

        recyclerView = rootView.findViewById(R.id.rvDecks)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = DeckAdapter(rootView.context, this)

        adapter.setItems(decksViewModel.userDecks.value ?: emptyList())

        recyclerView.adapter = adapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDeckListener()
    }

    private fun initDeckListener() {
        decksViewModel.userDecks.observe(viewLifecycleOwner) {
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDeckClick(deck: Deck) {

    }

    override fun onDeckLongClick(deck: Deck) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(context?.getString(R.string.delete_deck_prompt))
            .setMessage(context?.getString(R.string.are_you_sure))
            .setPositiveButton(context?.getString(R.string.yes)) { _, _ ->
                decksViewModel.delete(deck)
            }
            .setNegativeButton(context?.getString(R.string.no), null)
            .show()
    }
}