package hr.algebra.pbadanjak.flashcards.factory

import android.content.Context
import hr.algebra.pbadanjak.flashcards.dao.FlashcardsSqlHelper

fun getFlashcardsRepository(context: Context?) = FlashcardsSqlHelper(context)