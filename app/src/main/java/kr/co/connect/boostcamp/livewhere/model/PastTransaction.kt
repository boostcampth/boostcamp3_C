package kr.co.connect.boostcamp.livewhere.model

data class PastTransaction(
    val buildingName: String,
    val price: String,
    val area: String,
    val type: String,
    val contractYear: String
)




class CompareByArea {

    companion object : Comparator<PastTransaction> {

        override fun compare(a: PastTransaction , b: PastTransaction): Int = when {
            a.area != b.area -> Integer.parseInt(b.area) - Integer.parseInt(a.area)
            else -> 0
        }
    }
}

class CompareByAreaRev {

    companion object : Comparator<PastTransaction> {

        override fun compare(a: PastTransaction , b: PastTransaction): Int = when {
            a.area != b.area -> Integer.parseInt(a.area) - Integer.parseInt(b.area)
            else -> 0
        }
    }
}
class CompareByType {

    companion object : Comparator<PastTransaction> {

        override fun compare(a: PastTransaction , b: PastTransaction): Int = when {
            a.type != b.type && a.type=="월세" -> 1
            a.type != b.type && a.type=="전세" -> -1
            else -> 0
        }
    }
}

class CompareByTypeRev {

    companion object : Comparator<PastTransaction> {

        override fun compare(a: PastTransaction , b: PastTransaction): Int = when {
            a.type != b.type && a.type=="월세" -> -1
            a.type != b.type && a.type=="전세" -> 1
            else -> 0
        }
    }
}

class CompareByYear {

    companion object : Comparator<PastTransaction> {

        override fun compare(a: PastTransaction , b: PastTransaction): Int = when {
            a.contractYear!= b.contractYear -> Integer.parseInt(b.contractYear) - Integer.parseInt(a.contractYear)
            else -> 0
        }
    }
}

class CompareByYearRev {

    companion object : Comparator<PastTransaction> {

        override fun compare(a: PastTransaction , b: PastTransaction): Int = when {
            a.contractYear!= b.contractYear -> Integer.parseInt(a.contractYear) - Integer.parseInt(b.contractYear)
            else -> 0
        }
    }
}