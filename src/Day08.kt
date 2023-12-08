fun main() {

    fun parseInput1(input: List<String>): Int {
        val instructions = input[0]

        val nodes = input.subList(2, input.size).associate {
            val split = it.split("=")
            val secondPart = split[1].replace("[\\s+()]".toRegex(), "").split(",")
            split[0].trim() to (secondPart[0] to secondPart[1])
        }

        var counter = 0
        var currentSymbol = "AAA"

        while (true) {
            val goal = "ZZZ"
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

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    check(part1(testInput1) == 2)
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput2) == 6)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
