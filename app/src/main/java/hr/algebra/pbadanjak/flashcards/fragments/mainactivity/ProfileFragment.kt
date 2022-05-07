package hr.algebra.pbadanjak.flashcards.fragments.mainactivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.type.Date
import com.google.type.DateTime
import hr.algebra.pbadanjak.flashcards.LoginActivity
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.framework.startActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.time.LocalDate

class ProfileFragment : Fragment() {

    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvDateJoined: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        tvUsername = requireActivity().findViewById(R.id.tvUsername)
        tvEmail = requireActivity().findViewById(R.id.tvEmail)
        tvDateJoined = requireActivity().findViewById(R.id.tvDateJoined)

        tvUsername.text = auth.currentUser?.displayName ?: "Not set"
        tvEmail.text = auth.currentUser?.email

        val sdf = SimpleDateFormat("dd/MM/yyyy'@'hh:mm.ss")
        val date = auth.currentUser?.metadata?.creationTimestamp.let { java.util.Date(it ?: -1) }
        tvDateJoined.text = sdf.format(date)
    }
}