package hr.algebra.pbadanjak.flashcards.fragments.mainactivity.carddeckcreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import hr.algebra.pbadanjak.flashcards.R

class CardDeckCreatorSheet : BottomSheetDialogFragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val modalView: View = inflater.inflate(R.layout.bottom_sheet_modal, container, false)

        initTabLayout(modalView)
        initViewPager(modalView)

        return modalView
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = CardDeckCreatorFragmentAdapter(activity as AppCompatActivity)
        viewPager.isUserInputEnabled = false

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (tabLayout.selectedTabPosition != viewPager.currentItem)
                    viewPager.currentItem = tabLayout.selectedTabPosition
            }
        })
    }

    private fun initTabLayout(view: View) {
        tabLayout = view.findViewById(R.id.tab_layout)
    }
}