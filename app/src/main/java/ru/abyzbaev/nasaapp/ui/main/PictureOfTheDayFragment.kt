package ru.abyzbaev.nasaapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import ru.abyzbaev.nasaapp.PictureOfTheDayData
import ru.abyzbaev.nasaapp.R
import ru.abyzbaev.nasaapp.databinding.FragmentMainBinding

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData().observe(viewLifecycleOwner){ renderData(it) }
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun renderData(data: PictureOfTheDayData){
        when(data){
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()){
                    //Отобразить ошибку
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                }else{
                    //Отобразите фото
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView нужную
                    //extension-функцию и передать ссылку на изображение
                    //а в лямбде указать дополнительные параметры  (не обязательно)
                    // для отображения ошибки, процесса загрузки, анимации смены изображений
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }
                }
            }
            is PictureOfTheDayData.Loading ->{
                //Отобразите загрузку
                // showLoading()
            }
            is PictureOfTheDayData.Error ->{
                //Отобразите ошибку
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun toast(string: String?){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}