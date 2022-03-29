/**
 * This represents a node in the A* search for finding the shortest Knight path.
 *
 * @param parent The square from which the knight came, in the best known path to `coordinate`.
 * @param coordinate The current square of this node on the board.
 * @param destination The destination square.
 */
class KnightPathNode(
    parent: KnightPathNode?,
    val coordinate: ChessBoard.Coordinate,
    destination: ChessBoard.Coordinate,
) : Comparable<KnightPathNode> {
    var parent = parent
        set(value) {
            field = value
            gCost = if (value == null) 0 else value.gCost + 1
        }

    // Visiting is a process of checking all the neighbors and adding them to a priority queue.
    // It only needs to be done once for a given `coordinate`, so it won't be visited again once
    // `visited` is set to true.
    var visited = false

    // gCost is the realized cost, which is the number of moves so far in the best known path
    private var gCost: Int = if (parent == null) 0 else parent.gCost + 1

    // hCost is the predicted cost to get from the current square to the destination square
    private val hCost = knightPathHeuristic(start = coordinate, end = destination)

    // fCost is a measure of the total expected cost to travel through this node
    private val fCost get() = gCost + hCost

    /**
     * This defines how `KnightPathNode`s are ordered in a PriorityQueue.
     *
     * We have to prioritize the lowest fCost nodes first to guarantee an optimal solution, but
     * when two solutions have the same fCost, we prefer higher gCosts, as that just picks one of
     * the multiple equally good solutions and follows it, rather than looking at all the equally good paths.
     */
    override fun compareTo(other: KnightPathNode) = compareValuesBy(this, other, { it.fCost }, { -1 * it.gCost })

    /**
     * Indicates whether this node is a better `parent` for the `neighbor` than the parent
     * that the neighbor currently has, meaning this node provides a shorter path.
     */
    fun providesShorterPathTo(neighbor: KnightPathNode) = this.gCost + 1 < neighbor.gCost

    /**
     * Gives a list of the coordinates traversed along the path that follows the `parent` nodes.
     */
    fun getPath(): List<ChessBoard.Coordinate> {
        val path = mutableListOf<ChessBoard.Coordinate>()
        var next: KnightPathNode? = this
        while (next != null) {
            path.add(next.coordinate)
            next = next.parent
        }
        return path
    }
}

