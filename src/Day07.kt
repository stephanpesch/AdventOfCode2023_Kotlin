fun main() {
    class Hand(val cardString: String): Comparable<Hand> {
        var hash: Int = 0
        init {
            val cardCounts = mutableMapOf<Char, Int>()
            cardString.forEach {
                cardCounts[it] = cardCounts.getOrDefault(it, 0) + 1
            }
            val test = cardCounts.values.sorted().asReversed()
            val classification = when {
                test[0] == 1 -> 0 // High card
                test[0] == 2 && test[1] != 2 -> 1 // One pair
                test[0] == 2 && test[1] == 2 -> 2 // Two pair
                test[0] == 3 && test[1] != 2 -> 3 // Three of a kind
                test[0] == 3 && test[1] == 2 -> 4 // Full house
                test[0] == 4 -> 5 // Four of a kind
                else -> 6 // Five of a kind
            }
            val charMapper = mapOf(
                '2' to '0',
                '3' to '1',
                '4' to '2',
                '5' to '3',
                '6' to '4',
                '7' to '5',
                '8' to '6',
                '9' to '7',
                'T' to '8',
                'J' to '9',
                'Q' to 'A',
                'K' to 'B',
                'A' to 'C'
            )
            hash = (classification.toString() + cardString.toCharArray().map {
                charMapper[it]
            }.joinToString(separator = "")).toInt(radix = 13)
        }

        override fun compareTo(other: Hand): Int {
            return hash - other.hash
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Hand

            return hash == other.hash
        }

        override fun hashCode(): Int {
            return hash
        }
    }
    data class HandBid(val hand: Hand, val bid: Int)

    fun part1(input: List<String>): Int {
        val test = input.map {
            val split = it.split(" ")
            HandBid(Hand(split[0]), split[1].toInt())
        }
            .sortedBy { it.hand }

        var sum = 0
        test.forEachIndexed { index, handBid ->
            sum += handBid.bid * (index + 1)
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
