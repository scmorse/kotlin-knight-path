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
    fun addKnightPathNode(parent: KnightPathNode?, coordinate: ChessBoard.Coordinate) {
        KnightPathNode(parent, coordinate, destination = start).let {
            queue.add(it)
            board[coordinate] = it
        }
    }
    addKnightPathNode(parent = null, coordinate = end)

    while (!queue.isEmpty()) {
        val current = queue.remove()
        if (current.invalidated) {
            continue
        }
        current.visited = true

        // we started at the `end`, so if we made it to `start`, then we found the best path
        if (current.coordinate == start) {
            break
        }

        for (neighborCoordinate in board.getKnightMovesFrom(current.coordinate)) {
            val neighbor = board[neighborCoordinate]
            if (neighbor == null) {
                addKnightPathNode(parent = current, coordinate = neighborCoordinate)
                continue
            }

            if (neighbor.visited) {
                continue
            }

            if (current.providesShorterPathTo(neighbor)) {
                // This means that we found a shorter path to a `neighbor` by going through
                // `current` rather than the best-known path through `neighbor.parent`.
                // Typically, we would want to set the `neighbor.parent` to `current`, but
                // priority queues don't work well when their nodes change order suddenly.
                // So instead the KnightPathNode class is immutable (in its properties that
                // determine order), and we have to add a new node with the shorter path.
                addKnightPathNode(parent = current, coordinate = neighborCoordinate)

                // We would also want to remove the old node from the queue, to avoid processing
                // it again. But, removing from the heap is an expensive operation, O(n) each time,
                // mainly because we have to find it in the heap to remove it. So in this situation,
                // it's actually more efficient to leave the old-path in the heap, but tag it as
                // invalidated so that we skip it if it ever rises to the top of the queue.
                neighbor.invalidated = true
            }
        }
    }

    return board[start]?.getPath()
}

