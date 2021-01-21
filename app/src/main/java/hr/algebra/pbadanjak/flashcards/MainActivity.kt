package hr.algebra.pbadanjak.flashcards

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.pbadanjak.flashcards.framework.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) startLoginActivity()
    }

    private fun startLoginActivity() =
        startActivity<LoginActivity>()
}