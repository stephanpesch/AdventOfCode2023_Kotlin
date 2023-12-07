class Hand(cardString: String, private val part: Int): Comparable<Hand> {
    private var hash: Int = -1
    private val charMapper1 = mapOf(
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

    private val charMapper2 = mapOf(
        '2' to '1',
        '3' to '2',
        '4' to '3',
        '5' to '4',
        '6' to '5',
        '7' to '6',
        '8' to '7',
        '9' to '8',
        'T' to '9',
        'J' to '0',
        'Q' to 'A',
        'K' to 'B',
        'A' to 'C'
    )
    init {
        val cardCounts = mutableMapOf<Char, Int>()
        cardString.forEach {
            cardCounts[it] = cardCounts.getOrDefault(it, 0) + 1
        }
        val classification = calculateClassification(cardCounts)
        hash = calculateHash(classification, cardString)
    }

    private fun calculateClassification(cardCounts: Map<Char, Int>): Int {
        return if (part == 1 || cardCounts.getOrDefault('J', 0) == 0) {
            val test = cardCounts.values.sorted().asReversed()
            when {
                test[0] == 1 -> 0 // High card
                test[0] == 2 && test[1] != 2 -> 1 // One pair
                test[0] == 2 && test[1] == 2 -> 2 // Two pair
                test[0] == 3 && test[1] != 2 -> 3 // Three of a kind
                test[0] == 3 && test[1] == 2 -> 4 // Full house
                test[0] == 4 -> 5 // Four of a kind
                else -> 6 // Five of a kind
            }
        } else {
            val jokerCount = cardCounts['J']
            val withoutJoker = cardCounts.filter { (key, _) -> key != 'J' }
            val test = withoutJoker.values.sorted().asReversed()
            when (jokerCount) {
                1 -> handleOneJoker(test)
                2 -> handleTwoJokers(test)
                3 -> handleThreeJokers(test)
                else -> 6
            }
        }
    }

    private fun handleOneJoker(values: List<Int>): Int {
        return when {
            values[0] == 1 -> 1 // One pair
            values[0] == 2 && values[1] != 2 -> 3 // Three of a kind
            values[0] == 2 && values[1] == 2 -> 4 // Full house
            values[0] == 3 && values[1] != 2 -> 5 // Four of a kind
            else -> 6 // Five of a kind
        }
    }
    private fun handleTwoJokers(values: List<Int>): Int {
        return when {
            values[0] == 1 -> 3 // Three of a kind
            values[0] == 2 && values[1] != 2 -> 5 // Four of a kind
            else -> 6 // Five of a kind
        }
    }
    private fun handleThreeJokers(values: List<Int>): Int {
        return when {
            values[0] == 1 -> 5 // Four of a kind
            else -> 6 // Four of a kind
        }
    }

    private fun calculateHash(classification: Int, cardString: String): Int {
        val charMapper = if (part == 1) charMapper1 else charMapper2
        return (classification.toString() + cardString.toCharArray().map {
            charMapper[it]
        }.joinToString(separator = "")).toInt(radix = 13)
    }

    override fun compareTo(other: Hand): Int {
        return hashCode() - other.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hand

        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return hash
    }
}

data class HandBid(val hand: Hand, val bid: Int)

fun main() {

    fun part1(input: List<String>): Int {
        val test = input.map {
            val split = it.split(" ")
            HandBid(Hand(split[0], 1), split[1].toInt())
        }
            .sortedBy { it.hand }

        var sum = 0
        test.forEachIndexed { index, handBid ->
            sum += handBid.bid * (index + 1)
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val test = input.map {
            val split = it.split(" ")
            HandBid(Hand(split[0], 2), split[1].toInt())
        }
            .sortedBy { it.hand }

        var sum = 0
        test.forEachIndexed { index, handBid ->
            sum += handBid.bid * (index + 1)
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
