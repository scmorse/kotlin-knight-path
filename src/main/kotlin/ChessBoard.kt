/**
 * Essentially an NxM grid that can hold information of type T.
 * The provided dimensions dictate which coordinates are valid.
 *
 * Example:
 *
 *     0   1   2   3
 * 0 | - | - | - | - |
 * 1 | - | - | - | - |
 * 2 | - | - | - | - |
 *
 * (3, 2) is a valid board coordinate here
 * (2, 3) is not
 */
class ChessBoard<T> private constructor(
    val dimensions: ChessBoardDimensions,
    private val entriesMap: MutableMap<Coordinate, T?>
) : MutableMap<ChessBoard.Coordinate, T?> by entriesMap {

    // delegation using `by` only available to constructor properties in kotlin, so this
    // is a workaround to declare mapEntries as a constructor param
    constructor(dimensions: ChessBoardDimensions) : this(dimensions, mutableMapOf())

    data class Coordinate(val x: Int, val y: Int) {
        override fun toString() = "($x, $y)"
    }

    fun getKnightMovesFrom(coordinate: Coordinate) = iterator {
        for (delta in KNIGHT_DELTAS) {
            val neighbor = Coordinate(coordinate.x + delta.first, coordinate.y + delta.second)
            if (dimensions.isValidCoordinate(neighbor)) {
                yield(neighbor)
            }
        }
    }
}

private val KNIGHT_DELTAS = listOf(
    Pair(1, 2),
    Pair(2, 1),
    Pair(-1, 2),
    Pair(-2, 1),
    Pair(1, -2),
    Pair(2, -1),
    Pair(-1, -2),
    Pair(-2, -1)
)


