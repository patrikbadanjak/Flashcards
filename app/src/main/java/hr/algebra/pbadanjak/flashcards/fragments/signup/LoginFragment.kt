package hr.algebra.pbadanjak.flashcards.fragments.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import hr.algebra.pbadanjak.flashcards.MainActivity
import hr.algebra.pbadanjak.flashcards.R
import hr.algebra.pbadanjak.flashcards.RC_GOOGLE_SIGN_IN
import hr.algebra.pbadanjak.flashcards.framework.startActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val TAG = "LOGIN_FRAGMENT"

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        initGoogleSignIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "Firebase auth with google: " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private val loginOnClickListener: View.OnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.btnLogin -> signInWithUsernameAndPassword()
            R.id.btnLoginGoogle -> signInWithGoogle()
        }
    }

    private fun initListeners() {
        btnLogin.setOnClickListener(loginOnClickListener)
        btnLoginGoogle.setOnClickListener(loginOnClickListener)
        etPassword.setOnEditorActionListener{ _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> btnLogin.performClick()
                else -> false
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun signInWithUsernameAndPassword() {
        if (formIsValid()) {
            auth.signInWithEmailAndPassword(
                    etEmail.text.toString().trim(),
                    etPassword.text.toString().trim()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        requireActivity().run {
                            startActivity<MainActivity>()
                            finish()
                        }
                    } else {
                        AlertDialog.Builder(requireContext())
                            .setMessage(R.string.invalid_email_or_password)
                            .setNeutralButton("Ok", null)
                            .show()
                    }
                }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential: success")
                    requireContext().startActivity<MainActivity>()
                } else {
                    Log.w(TAG, "signInWithCredential: failure", task.exception)
                }
            }
    }

    private fun formIsValid(): Boolean {
        var valid = true

        if (etEmail.text.isNullOrEmpty() ||
            !Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()
        ) {
            etEmailWrapper.error = "Please enter a valid email "
            etEmail.requestFocus()
            valid = false
        } else if (etPassword.text.isNullOrEmpty()) {
            etPasswordWrapper.error = "Please enter a password "
            etPassword.requestFocus()
            valid = false
        }

        return valid
    }
}