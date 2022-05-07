package hr.algebra.pbadanjak.flashcards.fragments.mainactivity.carddeckcreator

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.model.Card
import hr.algebra.pbadanjak.flashcards.model.Deck
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel
import kotlinx.android.synthetic.main.fragment_card_creator.*

class CardCreatorFragment : Fragment() {

    private lateinit var textField: AutoCompleteTextView
    private val viewModel: DecksViewModel by viewModels()
    private var selectedDeck: Deck? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_creator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initTextField()
        initListeners()
    }

    private fun initListeners() {
        btnAdd.setOnClickListener {
            if (formIsValid()) {
                val card = Card(
                    etQuestion.text.toString().trim(),
                    etAnswer.text.toString().trim()
                )

                viewModel.saveCardToDeck(card, selectedDeck!!)

                resetForm()

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.success)
                    .setMessage(getString(R.string.card_added_successfully))
                    .setNeutralButton(R.string.ok, null)
                    .show()
            }
        }
        
        etAutocomplete.setOnItemClickListener { parent, _, position, _ ->
            selectedDeck = parent.getItemAtPosition(position) as? Deck
        }
    }

    private fun initTextField() {
        textField = requireView().findViewById(R.id.etAutocomplete)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            viewModel.userDecks.value ?: listOf(getString(R.string.no_decks_found))
        )

        textField.setAdapter(adapter)

        viewModel.userDecks.observe(viewLifecycleOwner) {
            textField.setAdapter(ArrayAdapter(requireContext(), R.layout.list_item, it))
        }
    }

    private fun formIsValid(): Boolean {
        var valid = true

        when {
            selectedDeck == null -> {
                etAutocompleteWrapper.error = getString(R.string.select_deck_prompt)
                valid = false
            }
            etQuestion.text.isNullOrBlank() -> {
                etQuestionWrapper.error = getString(R.string.write_question_prompt)
                valid = false
            }
            etAnswer.text.isNullOrBlank() -> {
                etAnswerWrapper.error = getString(R.string.write_answer_prompt)
                valid = false
            }
        }

        return valid
    }

    private fun resetForm() {
        etQuestion.text?.clear()
        etAnswer.text?.clear()
        etQuestion.requestFocus()
    }
}