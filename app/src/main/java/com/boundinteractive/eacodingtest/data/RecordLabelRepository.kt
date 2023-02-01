package com.boundinteractive.eacodingtest.data

import com.boundinteractive.eacodingtest.data.model.MusicFestivalDto
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.convertToList
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.sortToMap
import com.boundinteractive.eacodingtest.ui.data.RecordLabel
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import timber.log.Timber
import javax.inject.Inject

class RecordLabelRepository @Inject constructor(retrofit: Retrofit) {

    private val festivalsApi = retrofit.create(FestivalsApi::class.java)

    val festivals = MutableSharedFlow<RecordLabelApiResponse>()

    fun getRecordLabels() {
        return festivalsApi
            .getFestivals()
            .enqueue(object : Callback<List<MusicFestivalDto>> {
                override fun onResponse(
                    call: Call<List<MusicFestivalDto>>,
                    response: Response<List<MusicFestivalDto>>
                ) {
                    response.body()?.let {
                        try {
                            festivals.tryEmit(
                                RecordLabelApiResponse.Success(
                                    it.sortToMap().convertToList()
                                )
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                            festivals.tryEmit(RecordLabelApiResponse.Failure("Success with null body"))
                        }
                    }
                        ?: festivals.tryEmit(RecordLabelApiResponse.Failure("Success with null body"))
                }

                override fun onFailure(call: Call<List<MusicFestivalDto>>, t: Throwable) {
                    festivals.tryEmit(RecordLabelApiResponse.Failure(t.message ?: "Something went wrong"))
                }
            })
    }

}

sealed class RecordLabelApiResponse {
    class Success(val data: List<RecordLabel>) : RecordLabelApiResponse()
    class Failure(val message: String) : RecordLabelApiResponse()
}

interface FestivalsApi {
    @GET("festivals/")
    fun getFestivals(): Call<List<MusicFestivalDto>>
}

