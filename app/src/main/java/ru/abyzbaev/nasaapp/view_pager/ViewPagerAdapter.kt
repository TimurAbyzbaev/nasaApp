package ru.abyzbaev.nasaapp.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.abyzbaev.nasaapp.ui.main.fragments.EarthFragment
import ru.abyzbaev.nasaapp.ui.main.fragments.MarsFragment
import ru.abyzbaev.nasaapp.ui.main.fragments.WeatherFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[WEATHER_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val WEATHER_FRAGMENT = 2
    }
}