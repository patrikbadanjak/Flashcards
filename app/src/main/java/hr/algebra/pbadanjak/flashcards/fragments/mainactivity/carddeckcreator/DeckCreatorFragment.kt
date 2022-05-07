package hr.algebra.pbadanjak.flashcards.fragments.mainactivity.carddeckcreator

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import hr.algebra.pbadanjak.flashcards.LoginActivity
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.framework.showSnackBar
import hr.algebra.pbadanjak.flashcards.framework.startActivity
import hr.algebra.pbadanjak.flashcards.model.Deck
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel
import kotlinx.android.synthetic.main.fragment_deck_creator.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class DeckCreatorFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var tags = ArrayList<String>()

    private val viewModel: DecksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_deck_creator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        etTag.setOnEditorActionListener { _, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> btnAddTag.performClick()
                else -> false
            }
        }

        btnAddTag.setOnClickListener {
            if (etTag.text.isNullOrBlank())
                return@setOnClickListener

            if (tags.contains(etTag.text.toString().trim()))
                Toast.makeText(context, getString(R.string.tag_already_added), Toast.LENGTH_LONG).show()
            else
                makeATag(etTag.text.toString())

            etTag?.text?.clear()
        }

        btnCreateDeck.setOnClickListener {
            try {
                if (formIsValid()) {
                    val dateCreated = Date()
                    val deck = Deck(
                        UUID.randomUUID().toString(),
                        auth.currentUser?.uid ?: throw Exception(getString(R.string.user_not_logged_in)),
                        etDeckName.text.toString().trim(),
                        switchPublic.isChecked,
                        dateCreated,
                        dateCreated,
                        tags,
                        listOf()
                    )
                    saveDeckToDatabase(deck)
                }
            } catch (e: Exception) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.error))
                    .setMessage(e.message)
                    .setNeutralButton(getString(R.string.ok)) { _, _ ->
                        requireContext().startActivity<LoginActivity>()
                    }
                    .show()
            }
        }
    }

    private fun makeATag(tagText: String) {
        val chip = Chip(context)
        chip.text = tagText.trim()
        chip.setOnClickListener {
            cgTags.removeView(it)
            tags.remove(tagText.trim())
        }
        tags.add(tagText)
        cgTags.addView(chip)
    }

    private fun formIsValid(): Boolean {
        var valid = true

        if (etDeckName.text.isNullOrBlank()) {
            etDeckName.error = getString(R.string.enter_deck_name_prompt)
            valid = false
        }

        return valid
    }

    private fun saveDeckToDatabase(deck: Deck) {
        viewModel.save(deck)

        clearForm()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.success))
            .setMessage(getString(R.string.deck_added_successfully))
            .setNeutralButton("Ok", null)
            .show()
    }

    private fun clearForm() {
        etDeckName.text?.clear()
        switchPublic.isChecked = false
        cgTags.removeAllViews()
        tags.clear()
        etTag.text?.clear()
    }
}