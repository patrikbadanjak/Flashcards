package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import hr.algebra.pbadanjak.flashcards.LoginActivity
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.framework.startActivity

class SettingsFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

    private lateinit var btnLogout: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListeners()
    }

    private fun init() {
        initViews()
        initListeners()
    }

    private fun initViews() {
        btnLogout = requireActivity().findViewById(R.id.btnLogout)
    }

    private fun initListeners() {
        btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Sign out?")
                .setMessage("Are you sure?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    requireContext().startActivity<LoginActivity>()
                }
                .show()
        }
    }
}