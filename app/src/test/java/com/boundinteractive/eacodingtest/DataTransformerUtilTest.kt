package com.boundinteractive.eacodingtest

import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.convertToList
import com.boundinteractive.eacodingtest.data.util.DataTransformerUtil.sortToMap
import com.google.common.truth.Truth
import org.junit.Test

class DataTransformerUtilTest {
    @Test
    fun givenBandWithoutRecordLabel_whenDataIsSorted_thenBandIsUnderEmptyRecordLabel() {
        val data = DataHelper.getFestivalsListForUnSignedArtists()
        val sortedData = data.sortToMap().convertToList()

        Truth.assertThat(sortedData[0].name).isEqualTo("")
    }

    @Test
    fun givenFestivalWithNullName_whenDataIsSorted_thenBandHasEmptyFestivalsList() {
        val data = DataHelper.getNullFestivalNameData()
        val sortedData = data.sortToMap().convertToList()

        Truth.assertThat(sortedData[0].bands[0].festivals).isEmpty()
    }

    @Test
    fun givenCleanSingleFestivalList_whenDataIsSorted_thenDataIsInProperFormat() {
        val data = DataHelper.getCleanSingleFestivalList()
        val sortedData = data.sortToMap().convertToList()

        Truth.assertThat(sortedData.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].name).isEqualTo("Record Label")
        Truth.assertThat(sortedData[0].bands.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].bands[0].name).isEqualTo("test band")
        Truth.assertThat(sortedData[0].bands[0].festivals.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].bands[0].festivals[0]).isEqualTo("festival1")
    }

    @Test
    fun givenMultipleFestivalSameRecordLabelList_whenDataIsSorted_thenBandsHasMultipleFestivals() {
        val data = DataHelper.getMultipleFestivalSameRecordLabelSameBandList()
        val sortedData = data.sortToMap().convertToList()

        Truth.assertThat(sortedData.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].name).isEqualTo("Record Label")
        Truth.assertThat(sortedData[0].bands.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].bands[0].name).isEqualTo("test band")
        Truth.assertThat(sortedData[0].bands[0].festivals.size).isEqualTo(2)
        Truth.assertThat(sortedData[0].bands[0].festivals[0]).isEqualTo("festival1")
        Truth.assertThat(sortedData[0].bands[0].festivals[1]).isEqualTo("festival2")
    }

    @Test
    fun givenSingleFestivalSameRecordLabelMultipleBandList_whenDataIsSorted_thenRecordLabelHasMultipleBandsWithSingleFestivals() {
        val data = DataHelper.getSingleFestivalSameRecordLabelMultipleBandList()
        val sortedData = data.sortToMap().convertToList()

        Truth.assertThat(sortedData.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].name).isEqualTo("Record Label")
        Truth.assertThat(sortedData[0].bands.size).isEqualTo(2)
        Truth.assertThat(sortedData[0].bands[0].name).isEqualTo("test band 2")
        Truth.assertThat(sortedData[0].bands[1].name).isEqualTo("test band")
        Truth.assertThat(sortedData[0].bands[0].festivals.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].bands[0].festivals[0]).isEqualTo("festival1")
        Truth.assertThat(sortedData[0].bands[1].festivals[0]).isEqualTo("festival1")
    }

    @Test
    fun givenMultipleRecordLabelSingleFestivalList_whenDataIsSorted_thenMultipleRecordLabelHaveSingleBand() {
        val data = DataHelper.getMultipleRecordLabelSingleFestivalList()
        val sortedData = data.sortToMap().convertToList()

        Truth.assertThat(sortedData.size).isEqualTo(2)
        Truth.assertThat(sortedData[0].name).isEqualTo("Record Label")
        Truth.assertThat(sortedData[1].name).isEqualTo("Record Label 2")
        Truth.assertThat(sortedData[0].bands.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].bands[0].name).isEqualTo("test band")
        Truth.assertThat(sortedData[1].bands[0].name).isEqualTo("test band 2")
        Truth.assertThat(sortedData[0].bands[0].festivals.size).isEqualTo(1)
        Truth.assertThat(sortedData[1].bands[0].festivals.size).isEqualTo(1)
        Truth.assertThat(sortedData[0].bands[0].festivals[0]).isEqualTo("festival1")
        Truth.assertThat(sortedData[1].bands[0].festivals[0]).isEqualTo("festival1")
    }
}