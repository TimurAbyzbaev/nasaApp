package ru.abyzbaev.nasaapp.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.abyzbaev.nasaapp.EarthEpicData
import ru.abyzbaev.nasaapp.R
import ru.abyzbaev.nasaapp.databinding.FragmentEarthBinding
import ru.abyzbaev.nasaapp.models.EarthFragmentViewModel

class EarthFragment : Fragment() {

    private lateinit var binding: FragmentEarthBinding
    private val viewModel: EarthFragmentViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(EarthFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEarthBinding.inflate(inflater, container, false)
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        return binding.root
    }

    private fun renderData(data: EarthEpicData){
        when(data){
            is EarthEpicData.Success -> {
                val serverResponseData = data.serverResponseData
                val data = serverResponseData[0]
                if (data.identifier.isNullOrEmpty()) {
                    //Отобразить ошибку
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    showSuccess()

                    val imageUrl = "https://epic.gsfc.nasa.gov/archive/natural/${data.identifier.substring(0,4)}/${data.identifier.substring(4,6)}/${data.identifier.substring(6,8)}/jpg/${data.image}.jpg"
                    Log.d("ЗАПРОС", imageUrl)
                    //val imageUrl = "https://epic.gsfc.nasa.gov/archive/natural/2023/02/18/jpg/epic_1b_20230218023357.jpg"
                    binding.imageView.load(imageUrl) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }
                }

            }
            is EarthEpicData.Loading -> {
                //Отобразите загрузку
                showLoading()
            }
            is EarthEpicData.Error -> {
                //Отобразите ошибку
                //showError(data.error.message)
                Toast.makeText(context, data.error.message, Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun showSuccess() {
        this.binding.loading.loadingLayout.visibility = View.GONE
    }
    private fun showLoading() {
        this.binding.loading.loadingLayout.visibility = View.VISIBLE
    }
    private fun toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}