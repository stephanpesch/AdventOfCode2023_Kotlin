fun main() {

    fun String.hash(): Int {
        var hashValue = 0
        this.forEach {
            hashValue += it.code
            hashValue *= 17
            hashValue %= 256
        }
        return hashValue
    }

    class Lens(val string: String, var focalLength: Int) {

        val hashCode = this.hashCode()
        override fun hashCode(): Int {
            return string.hash()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            val otherCasted = other as Lens
            return string == otherCasted.string
        }

        fun focussingPower(slot: Int): Int {
            val focussingPower = (slot + 1) * (hashCode + 1) * focalLength
            return focussingPower
        }

        override fun toString(): String {
            return "$string $focalLength"
        }
    }

    fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf { it.hash() }
    }

    fun part2(input: List<String>): Int {
        val hashMap = List<MutableList<Lens>>(256) { mutableListOf() }
        input[0].split(",").forEach { s ->
            if (s.contains('=')) {
                val split = s.split('=')
                val lens = Lens(split[0], split[1].toInt())
                val indexOfLens = hashMap[lens.hashCode].indexOf(lens)
                if (indexOfLens != -1) {
                    hashMap[lens.hashCode][indexOfLens].focalLength = lens.focalLength
                } else {
                    hashMap[lens.hashCode].add(lens)
                }
            } else {
                val string = s.substringBefore('-')
                val lens = Lens(string, -1)
                hashMap[lens.hashCode].remove(lens)
            }
        }
        val focussingPowerAll = hashMap.sumOf {
            it.indices.sumOf { index ->
                it[index].focussingPower(index)
            }
        }
        return focussingPowerAll
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
