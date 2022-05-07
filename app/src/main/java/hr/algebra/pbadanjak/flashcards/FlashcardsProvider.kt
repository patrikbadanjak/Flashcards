package hr.algebra.pbadanjak.flashcards

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.pbadanjak.flashcards.dao.FlashcardsRepository
import hr.algebra.pbadanjak.flashcards.factory.getFlashcardsRepository

private const val AUTHORITY = "hr.algebra.pbadanjak.flashcards.provider"
private const val PATH = "decks"
val FLASHCARDS_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

private const val DECKS = 1
private const val DECK_ID = 2
private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, DECKS) //content://hr.algebra.pbadanjak.flashcards.provider/decks
    addURI(AUTHORITY, "$PATH/#", DECK_ID) //content://hr.algebra.pbadanjak.flashcards.provider/decks/1
    this
}

private const val CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH
private const val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH

class FlashcardsProvider : ContentProvider() {
    private lateinit var repository: FlashcardsRepository

    override fun onCreate(): Boolean {
        repository = getFlashcardsRepository(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

}