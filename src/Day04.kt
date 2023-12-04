import java.util.stream.Collectors
import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    fun String.toIntSet() = this.trim().split("\\s+".toRegex()).stream()
        .map { string ->
            string.toInt()
        }
        .collect(Collectors.toSet())

    fun parseInput(input: List<String>): Int {
        return input.stream()
            .map {
                val sides = it
                    .substringAfter(":")
                    .split("|")
                val leftSide = sides[0].toIntSet()
                val rightSide = sides[1].toIntSet()
                val inter = leftSide intersect rightSide
                if (inter.isEmpty())
                    0
                else
                    2f.pow(inter.size - 1).roundToInt()
            }
            .reduce(Int::plus).get()
    }

    fun part1(input: List<String>): Int {
        return parseInput(input)
    }



    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
