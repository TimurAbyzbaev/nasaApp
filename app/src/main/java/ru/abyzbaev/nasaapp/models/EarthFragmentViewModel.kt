package ru.abyzbaev.nasaapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.abyzbaev.nasaapp.BuildConfig
import ru.abyzbaev.nasaapp.EPICServerResponseData
import ru.abyzbaev.nasaapp.EarthEpicData
import ru.abyzbaev.nasaapp.PODRetrofitImpl

class EarthFragmentViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthEpicData> =
        MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {
    fun getData(): LiveData<EarthEpicData>{
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(){
        liveDataForViewToObserve.value =
            EarthEpicData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            EarthEpicData.Error(Throwable("You need API Key"))
        }
        else{
            retrofitImpl.getRetrofitImpl()
                .getEarthPhoto(apiKey).enqueue(object  : Callback<List<EPICServerResponseData>> {
                    override fun onResponse(
                        call: Call<List<EPICServerResponseData>>,
                        response: Response<List<EPICServerResponseData>>
                    ) {
                        if (response.isSuccessful ){
                            liveDataForViewToObserve.value =
                                EarthEpicData.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()){
                                liveDataForViewToObserve.value =
                                    EarthEpicData.Error(Throwable("Unidentified error"))
                            } else {
                                liveDataForViewToObserve.value =
                                    EarthEpicData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<EPICServerResponseData>>, t: Throwable) {
                        liveDataForViewToObserve.value = EarthEpicData.Error(t)
                    }
                })
        }
    }

}