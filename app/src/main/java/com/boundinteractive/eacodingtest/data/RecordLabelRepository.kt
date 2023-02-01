package com.boundinteractive.eacodingtest.data

import com.boundinteractive.eacodingtest.data.model.MusicFestivalDto
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.convertToList
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.sortToMap
import com.boundinteractive.eacodingtest.ui.data.RecordLabel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class RecordLabelRepository @Inject constructor(retrofit: Retrofit) {

    private val festivalsApi = retrofit.create(FestivalsApi::class.java)

    val recordLabels = MutableStateFlow<RecordLabelApiResponse?>(null)

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
                            recordLabels.tryEmit(
                                RecordLabelApiResponse.Success(
                                    it.sortToMap().convertToList()
                                )
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                            recordLabels.tryEmit(RecordLabelApiResponse.Failure("Data parsing error"))
                        }
                    }
                }

                override fun onFailure(call: Call<List<MusicFestivalDto>>, t: Throwable) {
                    if (t is IllegalStateException) {
                        recordLabels.tryEmit(
                            RecordLabelApiResponse.Success(emptyList())
                        )
                    } else {
                        recordLabels.tryEmit(
                            RecordLabelApiResponse.Failure(
                                t.message ?: "Something went wrong"
                            )
                        )
                    }
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

