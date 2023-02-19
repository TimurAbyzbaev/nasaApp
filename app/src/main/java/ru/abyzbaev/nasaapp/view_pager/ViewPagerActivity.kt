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
import ru.abyzbaev.nasaapp.R
import ru.abyzbaev.nasaapp.databinding.ActivityViewPagerBinding

class ViewPagerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        setHighlightedTab(EARTH)

        /*binding.tabLayout.getTabAt(EARTH)?.setIcon(R.drawable.ic_earth)
        binding.tabLayout.getTabAt(MARS)?.setIcon(R.drawable.ic_mars)
        binding.tabLayout.getTabAt(WEATHER)?.setIcon(R.drawable.ic_solar_system)*/
        //setCustomTabs()

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {  }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }
        })
    }

    private fun setHighlightedTab(position: Int){
        val layoutInflater = LayoutInflater.from(this@ViewPagerActivity)

        binding.tabLayout.getTabAt(EARTH)?.customView = null
        binding.tabLayout.getTabAt(MARS)?.customView = null
        binding.tabLayout.getTabAt(SOLAR)?.customView = null

        when(position){
            EARTH -> {
                setEarthTabHighlighted(layoutInflater)
            }
            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }
            SOLAR -> {
                setSolarTabHighlighted(layoutInflater)
            }
            else ->{
                setEarthTabHighlighted(layoutInflater)
            }
        }

    }

    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater){
        val earth = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_earth, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview).apply {
            setTextColor(ContextCompat.getColor(context, R.color.purple_700))
        }

        binding.tabLayout.getTabAt(EARTH)?.customView = earth

        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_mars, null)
        binding.tabLayout.getTabAt(SOLAR)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_solar, null)
    }
    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater){
        val mars = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview).apply {
            setTextColor(ContextCompat.getColor(context, R.color.red))
            //compoundDrawables[0].setTint(ContextCompat.getColor(context, R.color.red))
        }

        binding.tabLayout.getTabAt(MARS)?.customView = mars

        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_earth, null)
        binding.tabLayout.getTabAt(SOLAR)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_solar, null)
    }
    private fun setSolarTabHighlighted(layoutInflater: LayoutInflater){
        val solar = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_solar, null)
        solar.findViewById<AppCompatTextView>(R.id.tab_image_textview).apply {
            setTextColor(ContextCompat.getColor(context, R.color.yellow))
            //compoundDrawables[0].setTint(ContextCompat.getColor(context, R.color.yellow))
        }
        binding.tabLayout.getTabAt(SOLAR)?.customView = solar

        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_mars, null)
    }

    /*private fun setCustomTabs(){
        val layoutInflater = LayoutInflater.from(this)
        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_mars, null)
        binding.tabLayout.getTabAt(SOLAR)?.customView = layoutInflater.inflate(R.layout.activity_view_pager_custom_tab_solar, null)

    }*/

    companion object{
        private const val EARTH = 0
        private const val MARS = 1
        private const val SOLAR = 2

    }

}