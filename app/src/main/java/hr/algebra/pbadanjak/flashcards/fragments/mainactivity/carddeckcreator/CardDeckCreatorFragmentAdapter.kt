package hr.algebra.pbadanjak.flashcards.fragments.mainactivity.carddeckcreator

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CardDeckCreatorFragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CardCreatorFragment()
            1 -> DeckCreatorFragment()
            else -> CardCreatorFragment()
        }
    }

    override fun getItemCount() = 2
}