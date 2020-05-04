package videodemos.example.marketplace.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import videodemos.example.marketplace.ListingFragment
import videodemos.example.marketplace.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
//TODO: Refactor so that is not deprecated
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ListingFragment.newInstance()
            3 -> ProfileFragment.newInstance(position)
            else -> PlaceholderFragment.newInstance(position + 1)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return NUMBER_OF_TABS
    }

    companion object {
        private const val NUMBER_OF_TABS = 4

        private val TAB_TITLES = arrayOf(
            R.string.tab_listings,
            R.string.tab_inventory,
            R.string.tab_messages,
            R.string.tab_profile
        )
    }
}