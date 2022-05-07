package hr.algebra.pbadanjak.flashcards

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.algebra.pbadanjak.flashcards.fragments.signup.LoginFragment
import hr.algebra.pbadanjak.flashcards.fragments.signup.RegisterFragment

class LoginAdapter(activity: AppCompatActivity, private val itemsCount: Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> LoginFragment()
        }
    }

}