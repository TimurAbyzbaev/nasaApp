package ru.abyzbaev.nasaapp

sealed class EarthEpicData{
    data class Success(val serverResponseData: List<EPICServerResponseData>) :
        EarthEpicData()
    data class Error(val error: Throwable) : EarthEpicData()
    data class Loading(val progress: Int?) : EarthEpicData()

}
