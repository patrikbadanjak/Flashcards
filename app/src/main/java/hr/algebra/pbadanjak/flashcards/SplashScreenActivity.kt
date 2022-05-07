package hr.algebra.pbadanjak.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import hr.algebra.pbadanjak.flashcards.framework.*
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel
import kotlinx.android.synthetic.main.activity_splash_screen.*

const val DATA_IMPORTED = "hr.algebra.pbadanjak.flashcards.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimation()
        checkForLoginAndRedirect()
    }

    private fun startAnimation() {
        ivLogo.applyAnimation(R.anim.scale)
    }

    private fun checkForLoginAndRedirect() {
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            if (!getBooleanPreference(DATA_IMPORTED)) {
                Intent(this, FlashcardsService::class.java,).apply {
                    FlashcardsService.enqueueWork(this@SplashScreenActivity, this)
                }
            } else {
                startActivity<MainActivity>()
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed(
                {startActivity<LoginActivity>()},
                1500
            )
//            startActivity<LoginActivity>()
        }
    }
}