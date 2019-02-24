package kr.co.connect.boostcamp.livewhere.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class House(
    @SerializedName("LAND_CD") val landCode: String, // pnu
    @SerializedName("ACC_YEAR") val accYear: String, // 접수년도
    @SerializedName("BLDG_NM") val name: String, // 건물이름
    @SerializedName("DONG_NM") val dong: String, //OO동 (102동)
    @SerializedName("FLR_NO") val floor: String, // 층
    @SerializedName("RENT_AREA") val area: String, // 면적
    @SerializedName("RENT_CASE_NM") val rentCase: String, // 전/월세
    @SerializedName("RENT_GTN") val deposite: String, // 보증금
    @SerializedName("RENT_FEE") val fee: String, //월세
    @SerializedName("CNTRCT_YEAR") val contractYear: String, //계약연도
    @SerializedName("CNTRCT_DE") val contractYearMonth: String, //계약년월
    @SerializedName("ROOM_CNT") val roomCount: String // 방개수
) : Parcelable

class CompareByContractYM {
    companion object : Comparator<House> {

        override fun compare(a: House, b: House): Int = when {
            a.contractYearMonth != b.contractYearMonth -> Integer.parseInt(b.contractYearMonth) - Integer.parseInt(a.contractYearMonth)
            else -> 0
        }
    }
}