fun main() {

    fun isSymbol(input: Char) = input != '.' && !input.isDigit()

    fun isSymbolAdjacent(input: List<String>, x: Int, y: Int): Boolean {
        return when {
            x > 0 && isSymbol(input[y][x - 1]) -> true
            x < input[y].length - 1 && isSymbol(input[y][x + 1]) -> true
            y > 0 && isSymbol(input[y - 1][x]) -> true
            y < input.size - 1 && isSymbol(input[y + 1][x]) -> true

            x > 0 && y > 0 && isSymbol(input[y - 1][x - 1]) -> true
            x < input[y].length - 1 && y > 0 && isSymbol(input[y - 1][x + 1]) -> true
            x > 0 && y < input.size - 1 && isSymbol(input[y + 1][x - 1]) -> true
            x < input[y].length - 1 && y < input.size - 1 && isSymbol(input[y + 1][x + 1]) -> true

            else -> false
        }
    }

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
