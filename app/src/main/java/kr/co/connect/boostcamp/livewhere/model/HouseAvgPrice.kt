package kr.co.connect.boostcamp.livewhere.model

data class HouseAvgPrice(
    val year:Float,
    val avgPrice:Float
)

class SortByYear {

    companion object : Comparator<HouseAvgPrice> {

        override fun compare(a: HouseAvgPrice , b: HouseAvgPrice): Int = when {
            a.year!= b.year -> a.year.toInt() - b.year.toInt()
            else -> 0
        }
    }
}