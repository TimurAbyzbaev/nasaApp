package ru.abyzbaev.nasaapp.ui.main

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.abyzbaev.nasaapp.MainActivity
import ru.abyzbaev.nasaapp.PictureOfTheDayData
import ru.abyzbaev.nasaapp.R
import ru.abyzbaev.nasaapp.databinding.FragmentMainBinding

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    //private lateinit var themeId: Int

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData().observe(viewLifecycleOwner){ renderData(it) }

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        binding.buttonThm1.setOnClickListener{
            requireContext().setTheme(R.style.IndigoTheme)
            //o
            //onSaveInstanceState()
            requireActivity().recreate()
        }
        binding.buttonThm2.setOnClickListener{
            requireContext().setTheme(R.style.PinkTheme)
            requireActivity().recreate()
        }
    }

    //saving state
    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(MainActivity().THEME, )
    }*/


    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout){
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
                    showSuccess()
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
                    requireView().findViewById<TextView>(R.id.bottomSheetDescriptionHeader).text = serverResponseData.title
                    requireView().findViewById<TextView>(R.id.bottomSheetDescription).text = serverResponseData.explanation
                }
            }
            is PictureOfTheDayData.Loading ->{
                //Отобразите загрузку
                showLoading()
            }
            is PictureOfTheDayData.Error ->{
                //Отобразите ошибку
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun showSuccess() {
        this.binding.loading.loadingLayout.visibility = GONE
    }


    private fun showLoading() {
        this.binding.loading.loadingLayout.visibility = VISIBLE
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