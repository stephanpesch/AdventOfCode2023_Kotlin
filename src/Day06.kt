import kotlin.math.*

fun main() {
    /*
    fun calculateIntersection(seconds: Int, millimeterRecord: Int): Int {
        // The function for the distance depending on the time the button is pressed is
        // f(timePressed) = -timePressed^2 + seconds * timePressed
        // Winning timePressed can be calculated by the intersection between this function and the time of the previous record
        // millimeterRecord = -timePressed^2 + seconds * timePressed
        // timePressed^2 - seconds * timePressed + millimeterRecord

        val secondPart = sqrt((seconds / 2.0).pow(2) - millimeterRecord)
        val firstSolution = seconds / 2.0 + secondPart
        val secondSolution = seconds / 2.0 - secondPart
        val distance = floor(firstSolution) - floor(secondSolution)
        val correction = if(seconds % 2 == 0) 1 else 0
        return distance.toInt() - correction
    }

     */
    fun calculateIntersection(seconds: Int, millimeterRecord: Int): Int {
        val range = 0..seconds
        return range.zip(range.reversed())
            .filter {
                (it.first * it.second) > millimeterRecord
            }
            .count()
    }

    fun parseNumbers(line: String): List<Int> {
        return line
            .substringAfter(":")
            .trim()
            .split("\\s+".toRegex())
            .map { it.toInt() }
    }

    fun parse1(input: List<String>): Int {
        val times = parseNumbers(input[0])
        val distances = parseNumbers(input[1])
        return times.zip(distances)
            .map { calculateIntersection(it.first, it.second) }
            .reduce(Int::times)
    }

    fun part1(input: List<String>): Int {
        return parse1(input)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
