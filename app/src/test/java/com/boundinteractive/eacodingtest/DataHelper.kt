package com.boundinteractive.eacodingtest

import com.boundinteractive.eacodingtest.data.model.BandDto
import com.boundinteractive.eacodingtest.data.model.MusicFestivalDto

object DataHelper {
    fun getFestivalsListForUnSignedArtists() =
        listOf(
            MusicFestivalDto(
                name = "festival1",
                bands = listOf(
                    BandDto(name = "test band", recordLabel = null)
                )
            )
        )

    fun getNullFestivalNameData() =
        listOf(
            MusicFestivalDto(
                name = null,
                bands = listOf(
                    BandDto(name = "test band", recordLabel = "Record label")
                )
            )
        )

    fun getCleanSingleFestivalList() =
        listOf(
            MusicFestivalDto(
                name = "festival1",
                bands = listOf(
                    BandDto(name = "test band", recordLabel = "Record Label")
                )
            )
        )

    fun getMultipleFestivalSameRecordLabelSameBandList() =
        listOf(
            MusicFestivalDto(
                name = "festival1",
                bands = listOf(
                    BandDto(name = "test band", recordLabel = "Record Label")
                )
            ),
            MusicFestivalDto(
                name = "festival2",
                bands = listOf(
                    BandDto(name = "test band", recordLabel = "Record Label")
                )
            )
        )

    fun getSingleFestivalSameRecordLabelMultipleBandList() =
        listOf(
            MusicFestivalDto(
                name = "festival1",
                bands = listOf(
                    BandDto(name = "test band", recordLabel = "Record Label"),
                    BandDto(name = "test band 2", recordLabel = "Record Label")
                )
            )
        )

    fun getMultipleRecordLabelSingleFestivalList() =
        listOf(
            MusicFestivalDto(
                name = "festival1",
                bands = listOf(
                    BandDto(name = "test band", recordLabel = "Record Label"),
                    BandDto(name = "test band 2", recordLabel = "Record Label 2")
                )
            )
        )
}