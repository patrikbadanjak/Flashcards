package hr.algebra.pbadanjak.flashcards.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.pbadanjak.flashcards.model.Deck

private const val DB_NAME = "decks.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "decks"
private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Deck::deckId.name} text primary key autoincrement, " +
        "${Deck::name.name} text not null, " +
        "${Deck::isPublic.name} integer not null, " +
        "${Deck::dateAdded.name} text not null, " +
        "${Deck::lastChanged.name} text not null, " +
        "${Deck::tags.name} string not null" +
        "${Deck::userId.name} string not null" +
        "${Deck::cards.name} string not null" +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class FlashcardsSqlHelper(context: Context?)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    FlashcardsRepository {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?)
        = writableDatabase.delete(TABLE_NAME, selection, selectionArgs)

    override fun insert(values: ContentValues?)
        = writableDatabase.insert(TABLE_NAME, null, values)

    override fun query(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = readableDatabase
        .query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

    override fun update(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)

}