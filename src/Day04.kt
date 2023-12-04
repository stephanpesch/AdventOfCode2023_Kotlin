import java.util.stream.Collectors
import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    fun String.toIntSet() = this.trim().split("\\s+".toRegex()).stream()
        .map { string ->
            string.toInt()
        }
        .collect(Collectors.toSet())

    fun splitSides(input: String) = input
        .substringAfter(":")
        .split("|")

    fun parseInput1(input: List<String>): Int {
        return input.parallelStream()
            .map {
                val sides = splitSides(it)
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
        return parseInput1(input)
    }

    fun parseInput2(input: List<String>): Int {
        val winList = input.stream()
            .map {
                val sides = splitSides(it)
                val leftSide = sides[0].toIntSet()
                val rightSide = sides[1].toIntSet()
                val inter = leftSide intersect rightSide
                inter.size
            }
            .collect(Collectors.toList())
        val cardAmountList = List(winList.size) { 1 }.toMutableList()
        winList.forEachIndexed { index, wins ->
            for (i in index until index + wins) {
                cardAmountList[i + 1] = cardAmountList[i + 1] + cardAmountList[index]
            }
        }
        return cardAmountList.sum()
    }

    fun part2(input: List<String>): Int {
        return parseInput2(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
