package hr.algebra.pbadanjak.flashcards

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import hr.algebra.pbadanjak.flashcards.framework.startActivity

private const val JOB_ID = 1

class FlashcardsService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        FlashcardsFetcher(this).fetchItems()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
//            enqueueWork(context, FlashcardsService::class.java, JOB_ID, intent)
            context.startActivity<MainActivity>()
        }
    }
}