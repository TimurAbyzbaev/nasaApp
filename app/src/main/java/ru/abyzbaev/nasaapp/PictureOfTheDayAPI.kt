package ru.abyzbaev.nasaapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String):
            Call<PODServerResponseData>

    @GET("EPIC/api/natural/")
    fun getEarthPhoto(@Query("api_key") apiKey: String):
            Call<List<EPICServerResponseData>>

    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&")
    fun getMarsPhoto(@Query("api_key") apiKey: String):
            Call<PODServerResponseData>


}
