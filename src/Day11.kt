import kotlin.math.abs

typealias Coordinate = Pair<Long, Long>

fun main() {
    fun List<List<Char>>.toStarsCoordinates(): List<Coordinate> {
        val coordinates = mutableListOf<Coordinate>()
        this.forEachIndexed { i, charList ->
            charList.forEachIndexed { j, c ->
                if (c == '#')
                    coordinates.add(Coordinate(i.toLong(), j.toLong()))
            }
        }
        return coordinates.toList()
    }

    fun List<Coordinate>.expand(amount: Int): List<Coordinate> {
        val maxFirst = this.maxOf { it.first }
        val maxSecond = this.maxOf { it.second }
        var newUniverse = this
        var expandCounter = 0
        for (i in maxFirst downTo 0) {
            if (this.none { it.first == i }) {
                expandCounter++
                newUniverse = newUniverse.map {
                    if (it.first > i) Coordinate(it.first + amount, it.second) else Coordinate(it.first, it.second)
                }
            }

        }

        for (i in maxSecond downTo 0) {
            if (this.none { it.second == i }) {
                expandCounter++
                newUniverse = newUniverse.map {
                    if (it.second > i) Coordinate(it.first, it.second + amount) else Coordinate(it.first, it.second)
                }
            }

        }
        return newUniverse
    }

    fun Coordinate.distanceTo(coordinate: Coordinate): Long {
        return abs(coordinate.first - this.first) + abs(coordinate.second - this.second)
    }

    fun solve(input: List<String>, amount: Int): Long {
        val list = input
            .map { it.toList() }
            .toStarsCoordinates()
            .expand(amount)

        val distanceList = list.flatMap { outerIt ->
            list.map {innerIt ->
                outerIt.distanceTo(innerIt)
            }
        }
        val sum = distanceList.sum() / 2
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(solve(testInput, 1) == 374L)
    check(solve(testInput, 9) == 1030L)
    check(solve(testInput, 99) == 8410L)

    val input = readInput("Day11")
    solve(input, 1).println()
    solve(input, 999_999).println()
}
