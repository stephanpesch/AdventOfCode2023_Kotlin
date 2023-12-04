import java.util.Collections
import java.util.stream.Collectors
import kotlin.math.max

fun main() {
    class Game(gameString: String) {
        var redAmount: Int = 0
        var greenAmount: Int = 0
        var blueAmount: Int = 0

        init {
            gameString.split(", ")
                .stream()
                .forEach {
                    val splitString = it.trim().split(" ")
                    if (splitString[1] == "red") {
                        redAmount = splitString[0].toInt()
                    } else if (splitString[1] == "green") {
                        greenAmount = splitString[0].toInt()
                    } else {
                        blueAmount = splitString[0].toInt()
                    }
                }
        }

        val isValid
            get() = redAmount <= 12 && greenAmount <= 13 && blueAmount <= 14

        val power
            get() = redAmount * greenAmount * blueAmount
    }

    fun part1(input: List<String>): Int {
        val games = input.stream().map {
            it
                .substringAfter(":")
                .trim()
                .split(";")
                .stream()
                .map { listString ->
                    Game(listString)
                }
                .allMatch { game ->
                    game.isValid
                }
        }
            .collect(Collectors.toList())
        var indexSum = 0
        games.forEachIndexed { index, value ->
            if (value) indexSum += index + 1
        }

        return indexSum
    }

    fun part2(input: List<String>): Int {
        val games = input.stream().map {
            it
                .substringAfter(":")
                .trim()
                .split(";")
                .stream()
                .map { listString ->
                    Game(listString)
                }
                .reduce { t, u ->
                    val game = Game("0 red")
                    game.blueAmount = max(t.blueAmount, u.blueAmount)
                    game.redAmount = max(t.redAmount, u.redAmount)
                    game.greenAmount = max(t.greenAmount, u.greenAmount)
                    game
                }
                .map { game ->
                    game.power
                }
                .get()
        }
            .collect(Collectors.toList())
            .sum()

        return games
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
