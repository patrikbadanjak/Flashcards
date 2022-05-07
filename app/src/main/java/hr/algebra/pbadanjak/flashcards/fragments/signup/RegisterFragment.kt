package hr.algebra.pbadanjak.flashcards.fragments.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.algebra.pbadanjak.flashcards.MainActivity
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.framework.startActivity
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.*
import java.util.*
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var validationFields: Array<TextInputEditText>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListeners()
    }

    private fun init() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        validationFields = arrayOf(etUsername, etEmail, etPassword, etPasswordConfirm)
    }

    private fun initListeners() {
        etPasswordConfirm.setOnEditorActionListener{ _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> btnRegister.performClick()
                else -> false
            }
        }

        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private  fun registerUser() {
        if (formIsValid()) {
            auth.createUserWithEmailAndPassword(
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    db.collection("users")
                        .document(auth.currentUser!!.uid)
                        .set(mapOf("username" to etUsername.text.toString().trim()))

                    context?.startActivity<MainActivity>()
                }

            }
        }
    }

    private fun formIsValid(): Boolean {
        var valid = true;

        validationFields.forEach {
            if (it.text.isNullOrBlank()) {
                it.error = getString(R.string.required_field)
                valid = false
            }
        }

        if (etUsername.text?.length!! < 3) {
            etUsername.error = getString(R.string.username_requirement_message)
            etUsername.requestFocus()
            valid = false
        }
//        else if (usernameExists(etUsername.text.toString().trim())) {
//            etUsername.error = getString(R.string.username_exists)
//            etUsername.requestFocus()
//            valid = false }
        else if (!passwordIsValid(etPassword.text.toString())) {
            etPassword.error = getString(R.string.password_requirement_message)
            etPassword.requestFocus()
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = getString(R.string.enter_valid_email)
            etEmail.requestFocus()
            valid = false
        } else if (etPassword.text.toString() != etPasswordConfirm.text.toString()) {
            etPasswordConfirm.error = getString(R.string.password_doesnt_match)
            etPasswordConfirm.requestFocus()
            valid = false
        }

        return valid
    }

    private fun usernameExists(username: String): Boolean {
        var exists = false

        GlobalScope.launch {
            val querySnapshot =
                db.collection("users")
                    .whereIn("username", mutableListOf(username)).get()

            exists = if (querySnapshot.result != null) !querySnapshot.result!!.isEmpty else true
        }

        return exists
    }

    private fun passwordIsValid(password: String): Boolean {
        val passwordRegex = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=\\S+$)" +
            ".{8,}" +
            "$")

        return passwordRegex.matcher(password).matches()
    }
}