import java.util.stream.Collectors

class Mapper {
    //                              range      offset
    private val mapping: MutableMap<LongRange, Long> = mutableMapOf()

    fun addMapping(destinationStart: Long, sourceStart: Long, length: Long) {
        mapping[sourceStart .. sourceStart + length] = destinationStart - sourceStart
    }

    fun mapSourceToDestination(source: Long): Long {
        for ((range, offset) in mapping) {
            if (range.contains(source))
                return source + offset
        }
        return source
    }
}

fun main() {

    fun List<String>.getIndicesOfValue(value: String): List<Int> {
        return this.indices.filter { this[it] == value }
    }

    fun part1(input: List<String>): Int {
        val seeds = input[0]
            .substringAfter(":")
            .trim()
            .split(" ")
            .map {
                it.toLong()
            }
        val maps = mutableListOf<List<String>>()
        val mapsStrings = input.subList(2, input.size)
        var lastIndex = 0
        mapsStrings
            .getIndicesOfValue("")
            .forEach { index ->
                maps.add(mapsStrings.slice(lastIndex until index))
                lastIndex = index + 1
            }
        maps.add(mapsStrings.slice(lastIndex until mapsStrings.size))
        val mappers = maps.map {
            val mapper = Mapper()
            it.forEachIndexed { index, s ->
                if (index != 0) {
                    val splitString = s.split(" ")
                    mapper.addMapping(splitString[0].toLong(), splitString[1].toLong(), splitString[2].toLong())
                }
            }
            mapper
        }

        return seeds.minOfOrNull {
            var currentValue = it.toLong()
            for (mapper in mappers) {
                currentValue = mapper.mapSourceToDestination(currentValue)
            }
            currentValue
        }!!.toInt()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
