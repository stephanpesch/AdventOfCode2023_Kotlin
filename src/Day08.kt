import kotlin.math.max

fun main() {

    fun inputToMap (input: List<String>): Map<String, Pair<String, String>> {
        return input.subList(2, input.size).associate {
            val split = it.split("=")
            val secondPart = split[1].replace("[\\s+()]".toRegex(), "").split(",")
            split[0].trim() to (secondPart[0] to secondPart[1])
        }
    }

    fun parseInput1(input: List<String>): Int {
        val instructions = input[0]

        val nodes = inputToMap(input)

        var counter = 0
        var currentSymbol = "AAA"
        val goal = "ZZZ"

        while (true) {
            val currentInstruction = instructions[counter % instructions.length]
            val currentPair = nodes[currentSymbol]
            currentSymbol = if (currentInstruction == 'L')
                currentPair!!.first
            else
                currentPair!!.second
            counter++
            if (currentSymbol == goal) {
                break
            }
        }

        return counter
    }

    fun part1(input: List<String>): Int {
        return parseInput1(input)
    }

    fun lcm(i1: Long, i2: Long): Long {
        val larger = max(i1, i2)
        val maxLcm = i1 * i2
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % i1 == 0L && lcm % i2 == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0]
        val nodes = inputToMap(input)
        val startingNodes = nodes.keys.filter { it.endsWith('A') }

        val steps = startingNodes.map {
            var counter = 0L
            var currentSymbol = it
            val goal = 'Z'

            while (true) {
                val currentInstruction = instructions[(counter % instructions.length).toInt()]
                val currentPair = nodes[currentSymbol]
                currentSymbol = if (currentInstruction == 'L')
                    currentPair!!.first
                else
                    currentPair!!.second
                counter++
                if (currentSymbol.endsWith(goal)) {
                    break
                }
            }

            counter
        }

        return steps.reduce { acc, i -> lcm(acc, i) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    check(part1(testInput1) == 2)
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput2) == 6)
    val testInput3 = readInput("Day08_test3")
    check(part2(testInput3) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
