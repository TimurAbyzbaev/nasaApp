package ru.abyzbaev.nasaapp.view_pager

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.DrawableUtils
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayoutMediator
import ru.abyzbaev.nasaapp.R
import ru.abyzbaev.nasaapp.databinding.ActivityViewPagerBinding

class ViewPagerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        setTabs()
    }

    private fun setTabs(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = when(position){
                EARTH -> getString(R.string.earth_tab_text)
                MARS -> getString(R.string.mars_tab_text)
                SOLAR -> getString(R.string.solar_tab_text)
                else -> getString(R.string.earth_tab_text)
            }
            tab.icon = when (position){
                EARTH -> ContextCompat.getDrawable(this,R.drawable.ic_earth)
                MARS -> ContextCompat.getDrawable(this,R.drawable.ic_mars)
                SOLAR -> ContextCompat.getDrawable(this,R.drawable.ic_solar_system)
                else -> ContextCompat.getDrawable(this,R.drawable.ic_earth)
            }
        }.attach()
    }

    companion object{
        private const val EARTH = 0
        private const val MARS = 1
        private const val SOLAR = 2

    }

}