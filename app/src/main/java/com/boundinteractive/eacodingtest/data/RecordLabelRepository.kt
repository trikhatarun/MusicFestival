package com.boundinteractive.eacodingtest.data

import com.boundinteractive.eacodingtest.data.model.MusicFestivalDto
import com.boundinteractive.eacodingtest.data.service.FestivalsApi
import com.boundinteractive.eacodingtest.data.service.RecordLabelApiResponse
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.convertToList
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.sortToMap
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RecordLabelRepository @Inject constructor(val festivalsApi: FestivalsApi) {

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

