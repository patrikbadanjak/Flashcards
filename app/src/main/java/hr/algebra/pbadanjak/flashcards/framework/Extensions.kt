package hr.algebra.pbadanjak.flashcards.framework

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.startActivity() = Intent(this, T::class.java)