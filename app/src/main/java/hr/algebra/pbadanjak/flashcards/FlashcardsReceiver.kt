package hr.algebra.pbadanjak.flashcards

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.pbadanjak.flashcards.framework.setBooleanPreference
import hr.algebra.pbadanjak.flashcards.framework.startActivity

class FlashcardsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.setBooleanPreference(DATA_IMPORTED, true)
        context?.startActivity<MainActivity>()
    }
}