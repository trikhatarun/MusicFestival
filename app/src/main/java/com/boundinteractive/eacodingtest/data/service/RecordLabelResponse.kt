package com.boundinteractive.eacodingtest.data.service

import com.boundinteractive.eacodingtest.ui.data.RecordLabel

sealed class RecordLabelApiResponse {
    class Success(val data: List<RecordLabel>) : RecordLabelApiResponse()
    class Failure(val message: String) : RecordLabelApiResponse()
}