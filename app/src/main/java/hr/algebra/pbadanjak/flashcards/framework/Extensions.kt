package hr.algebra.pbadanjak.flashcards.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.preference.PreferenceManager
import android.view.View
import android.view.animation.AnimationUtils
import com.google.android.material.snackbar.Snackbar

inline fun <reified T : Activity> Context.startActivity() = startActivity(Intent(this, T::class.java))

fun showSnackBar(activity: Activity, message: String, duration: Int)
    = Snackbar.make(
        activity.window.decorView.findViewById(android.R.id.content),
        message,
        duration
    )

fun View.applyAnimation(resourceId: Int) = startAnimation(AnimationUtils.loadAnimation(context, resourceId))

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

inline fun<reified T: BroadcastReceiver> Context.sendBroadcast()
    = sendBroadcast(Intent(this, T::class.java))

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    if (network != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}