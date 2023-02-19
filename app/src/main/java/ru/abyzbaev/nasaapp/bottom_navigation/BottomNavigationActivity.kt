package ru.abyzbaev.nasaapp.bottom_navigation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.abyzbaev.nasaapp.R
import ru.abyzbaev.nasaapp.databinding.ActivityBottomNavigationViewBinding
import ru.abyzbaev.nasaapp.ui.main.fragments.EarthFragment
import ru.abyzbaev.nasaapp.ui.main.fragments.MarsFragment
import ru.abyzbaev.nasaapp.ui.main.fragments.WeatherFragment

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_earth -> {
                    Toast.makeText(this, "Earth", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, EarthFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, MarsFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_solar -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, WeatherFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, EarthFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    Toast.makeText(this, "Still Mars", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_view_solar -> {
                    Toast.makeText(this, "Still Solar", Toast.LENGTH_SHORT).show()
                    binding.bottomNavigationView.removeBadge(R.id.bottom_view_solar)
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
        /*binding.bottomNavigationView.selectedItemId = R.id.bottom_view_mars

        binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_earth)

        binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_solar)
        val badge = binding.bottomNavigationView.getBadge(R.id.bottom_view_solar)
        badge?.maxCharacterCount = 2
        badge?.number = 8*/

    }
}