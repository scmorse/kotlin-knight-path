import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val KNIGHT_DISTANCE: Array<Array<Int>> = arrayOf(
    arrayOf(4, 3, 2, 3, 2, 3, 2, 3, 4),
    arrayOf(3, 2, 3, 2, 3, 2, 3, 2, 3),
    arrayOf(2, 3, 4, 1, 2, 1, 4, 3, 2),
    arrayOf(3, 2, 1, 2, 3, 2, 1, 2, 3),
    arrayOf(2, 3, 2, 3, 0, 3, 2, 3, 2),
    arrayOf(3, 2, 1, 2, 3, 2, 1, 2, 3),
    arrayOf(2, 3, 4, 1, 2, 1, 4, 3, 2),
    arrayOf(3, 2, 3, 2, 3, 2, 3, 2, 3),
    arrayOf(4, 3, 2, 3, 2, 3, 2, 3, 4),
)

/**
 * Gives an approximation of how many moves it will take to move a Knight from
 * the start position to the end position.
 *
 * This is both an [admissible](https://en.wikipedia.org/wiki/Admissible_heuristic)
 * and [consistent](https://en.wikipedia.org/wiki/Consistent_heuristic) heuristic.
 */
fun knightPathHeuristic(start: ChessBoard.Coordinate, end: ChessBoard.Coordinate): Double {
    val xDiff = end.x - start.x
    val yDiff = end.y - start.y

    // if `start` and `end` are close to each other, then using a pre-calculated
    // table significantly improves the accuracy
    KNIGHT_DISTANCE.getOrNull(4 + yDiff)?.getOrNull(4 + xDiff)?.let {
        return it.toDouble()
    }

    // but for longer distances, a rough estimate is good enough.
    val maxDiff = max(abs(xDiff), abs(yDiff))
    val minDiff = min(abs(xDiff), abs(yDiff))

    // when maxDiff is over 2x as big as minDiff, the knight uses its longer L-hop side
    // to travel the full maxDiff distance
    if (minDiff <= maxDiff / 2.0) {
        return maxDiff / 2.0
    }

    // but when minDiff and maxDiff are more similarly sized, then partway along the knight's
    // journey there will be a change in which axis the longer L-hop side is traveled.
    // For example, on a 9x9 board:
    //    | 0  ·  ·  ·  ·  ·  ·  ·  ·  · |
    //    | ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
    //    | ·  1  ·  ·  ·  ·  ·  ·  ·  · |
    //    | ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
    //    | ·  ·  2  ·  ·  ·  ·  ·  ·  · |
    //    | ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
    //    | ·  ·  ·  3  ·  ·  ·  ·  ·  · |
    //    | ·  ·  ·  ·  ·  4  ·  ·  ·  · |
    //    | ·  ·  ·  ·  ·  ·  ·  5  ·  · |
    //    | ·  ·  ·  ·  ·  ·  ·  ·  ·  E |  <- (9 + 9) / 3 == 6 moves
    // In this case, there's some clever geometrical math to show the approximate
    // number of moves is given by:
    return (minDiff + maxDiff) / 3.0
}
