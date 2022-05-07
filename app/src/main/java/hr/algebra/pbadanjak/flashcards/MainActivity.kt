package hr.algebra.pbadanjak.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import hr.algebra.pbadanjak.flashcards.dao.FirebaseHelper
import hr.algebra.pbadanjak.flashcards.fragments.mainactivity.carddeckcreator.CardDeckCreatorSheet
import hr.algebra.pbadanjak.flashcards.model.Deck
import hr.algebra.pbadanjak.flashcards.viewmodel.DecksViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var cardDeckCreatorSheet: CardDeckCreatorSheet

    private val viewModel: DecksViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.userDecks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initBottomNav()
        initBottomSheet()
        initListeners()
    }

    private fun initBottomSheet() {
        cardDeckCreatorSheet = CardDeckCreatorSheet()
    }

    private fun initListeners() {
        fab_create.setOnClickListener {
            cardDeckCreatorSheet.show(supportFragmentManager, "MAIN_ACTIVITY")
        }
    }

    private fun initBottomNav() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment_container)

        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
    }

}