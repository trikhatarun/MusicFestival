package com.boundinteractive.eacodingtest.data.util

import com.boundinteractive.eacodingtest.data.model.MusicFestivalDto
import com.boundinteractive.eacodingtest.ui.data.Band
import com.boundinteractive.eacodingtest.ui.data.RecordLabel
import timber.log.Timber

object DataTransformerUtil {
    fun List<MusicFestivalDto>.sortToMap(): HashMap<String, HashMap<String, ArrayList<String>>> {
        val recordLabelMap = HashMap<String, HashMap<String, ArrayList<String>>>()
        this.forEach {
            val currentFestival = it.name
            it.bands.forEach { band ->
                if (recordLabelMap.contains(band.recordLabel)) {
                    val recordLabelsBandsMap = recordLabelMap[band.recordLabel]
                        ?: throw Exception("Contains record label with null map")

                    if (recordLabelsBandsMap.contains(band.name)) {
                        val bandFestivals = recordLabelsBandsMap[band.name]
                            ?: throw Exception("Contains band with null festival list")
                        currentFestival?.let { festival -> bandFestivals.add(festival) }
                    } else {
                        val festivalList = arrayListOf<String>()
                            .apply {
                                currentFestival?.let { festival -> add(festival) }
                            }
                        recordLabelsBandsMap[band.name] = festivalList
                    }

                } else {
                    val bandMap = HashMap<String, ArrayList<String>>()
                    val festivalList = arrayListOf<String>()
                        .apply {
                            currentFestival?.let { festival -> add(festival) }
                        }
                    bandMap[band.name] = festivalList
                    recordLabelMap[band.recordLabel] = bandMap
                }
            }
        }
        return recordLabelMap
    }

    fun HashMap<String, HashMap<String, ArrayList<String>>>.convertToList() = this.map {
        val currentBands = it.value.map { bandMap ->
            Band(
                name = bandMap.key,
                festivals = bandMap.value
            )
        }
        RecordLabel(it.key, currentBands)
    }
}