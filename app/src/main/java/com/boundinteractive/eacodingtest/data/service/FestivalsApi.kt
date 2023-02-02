package com.boundinteractive.eacodingtest.data.service

import com.boundinteractive.eacodingtest.data.model.MusicFestivalDto
import retrofit2.Call
import retrofit2.http.GET

interface FestivalsApi {
    @GET("festivals/")
    fun getFestivals(): Call<List<MusicFestivalDto>>
}