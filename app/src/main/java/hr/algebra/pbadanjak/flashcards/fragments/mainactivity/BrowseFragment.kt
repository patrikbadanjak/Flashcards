package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.model.Deck
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel

class BrowseFragment : Fragment(), RecyclerViewDeckClickListener {

    private val decksViewModel: DecksViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeckAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        recyclerView = rootView.findViewById(R.id.rvDecks)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = DeckAdapter(rootView.context, this)

        adapter.setItems(decksViewModel.publicDecks.value ?: emptyList())

        recyclerView.adapter = adapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        decksViewModel.publicDecks.observe(viewLifecycleOwner) {
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDeckClick(deck: Deck) {
        Log.i("BROWSE_FRAGMENT", "Deck clicked from fragment: $deck")
    }

    override fun onDeckLongClick(deck: Deck) {
        Log.i("BROWSE_FRAGMENT", "Deck long clicked from fragment: $deck")
    }
}