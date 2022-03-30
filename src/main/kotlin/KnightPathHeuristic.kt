import kotlin.math.abs

val KNIGHT_DISTANCE: Array<Array<Int>> = arrayOf(
    arrayOf(0, 3, 2, 3, 2, 3),
    arrayOf(3, 2, 1, 2, 3, 4),
    arrayOf(2, 1, 4, 3, 2, 3),
    arrayOf(3, 2, 3, 2, 3, 4),
    arrayOf(2, 3, 2, 3, 4, 3),
    arrayOf(3, 4, 3, 4, 3, 4),
)

/**
 * Gives an approximation of how many moves it will take to move a Knight from
 * the start position to the end position.
 *
 * This is both an [admissible](https://en.wikipedia.org/wiki/Admissible_heuristic)
 * and [consistent](https://en.wikipedia.org/wiki/Consistent_heuristic) heuristic.
 */
fun knightPathHeuristic(start: ChessBoard.Coordinate, end: ChessBoard.Coordinate): Double {
    val xDiff = abs(end.x - start.x)
    val yDiff = abs(end.y - start.y)

    // if `start` and `end` are close to each other, then using a pre-calculated
    // table significantly improves the accuracy
    KNIGHT_DISTANCE.getOrNull(yDiff)?.getOrNull(xDiff)?.let {
        return it.toDouble()
    }

    // but for longer distances, a rough estimate is good enough.
    // when maxDiff is over 2x as big as minDiff, the knight uses its longer L-hop side
    // to travel the full maxDiff distance
    val (minDiff, maxDiff) = arrayOf(xDiff, yDiff).apply { sort() }
    if (minDiff * 2 <= maxDiff) {
        return maxDiff / 2.0
    }

    // but when minDiff and maxDiff are more similarly sized, then partway along the knight's
    // journey there will be a change in which axis the longer L-hop side is traveled.
    // For example, to move the knight between two squares where xDiff = 9 and yDiff = 9:
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
    // The Knight makes each move with a "manhattan distance" of 3 (2 vertical + 1 horizontal, for example),
    // so if all of those moves can make progress against either xDiff and yDiff (without any backtracking), then
    // this will be a good estimate. This works best at long distances, when the addition moves that require
    // some backtracking to line up with the target are negligible compared to the total number of moves.
    return (minDiff + maxDiff) / 3.0
}
