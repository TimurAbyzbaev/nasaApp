package ru.abyzbaev.nasaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.abyzbaev.nasaapp.databinding.ActivityMainBinding
import ru.abyzbaev.nasaapp.ui.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    val THEME = "THEME"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.PinkTheme)
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}