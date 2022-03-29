import java.util.*

/**
 * Finds the shortest path for a Knight to travel between two points
 * on a chess board, which has size given by `dimensions`. The path is found
 * using A* search.
 *
 * @param dimensions The size of the chess board
 * @param start The Knight's starting coordinate
 * @param end The Knight's destination coordinate
 * @return `null` if there is no possible path, otherwise the list of coordinates
 * that the Knight travels in one of the shortest paths from `start` to `end`.
 */
fun findShortestKnightPath(
    dimensions: ChessBoardDimensions,
    start: ChessBoard.Coordinate,
    end: ChessBoard.Coordinate
): List<ChessBoard.Coordinate>? {
    val queue = PriorityQueue<KnightPathNode>()
    val board = ChessBoard<KnightPathNode>(dimensions)

    // we're going to search from _end_ to _start_, keeping track of the previous node along
    // the way, so that when we get to the end we can follow the path of previous nodes, and
    // that produces a list in the correct order from _start_ to _end_
    KnightPathNode(
        parent = null,
        coordinate = end,
        destination = start,
    ).let {
        queue.add(it)
        board[end] = it
    }

    while (!queue.isEmpty()) {
        val current = queue.remove()
        current.visited = true

        // we started at the `end`, so if we made it to `start`, then we found the best path
        if (current.coordinate == start) {
            break
        }

        for (neighborCoordinate in board.getKnightMovesFrom(current.coordinate)) {
            val neighbor = board[neighborCoordinate]
            if (neighbor == null) {
                board[neighborCoordinate] = KnightPathNode(
                    parent = current,
                    coordinate = neighborCoordinate,
                    destination = start,
                )
                queue.add(board[neighborCoordinate])
                continue
            }

            if (neighbor.visited) {
                continue
            }

            if (current.providesShorterPathTo(neighbor)) {
                neighbor.parent = current
                queue.remove(neighbor)
                queue.add(neighbor)
            }
        }
    }

    return board[start]?.getPath()
}

