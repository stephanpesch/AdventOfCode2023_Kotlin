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

    fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf { it.hash() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
