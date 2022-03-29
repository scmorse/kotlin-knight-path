fun main(args: Array<String>) {
    val dimensions = args.readChessBoardDimensions("--board_size")
    val start = args.readCoordinate("--source")
    val end = args.readCoordinate("--dest")

    if (!dimensions.isValidCoordinate(start)) {
        System.err.println("$start is not a valid coordinate on a board of size $dimensions")
        return
    }

    if (!dimensions.isValidCoordinate(end)) {
        System.err.println("$end is not a valid coordinate on a board of size $dimensions")
        return
    }

    val path = findShortestKnightPath(dimensions, start, end)

    // no possible path found between start and end
    if (path == null) {
        println("-1")
        return
    }

    for ((i, coordinate) in path.withIndex()) {
        print("$coordinate")
        if (i != path.lastIndex) {
            print(" -> ")
        }
    }
    println("\n${path.size - 1}")
}

private fun Array<String>.readChessBoardDimensions(param: String): ChessBoardDimensions {
    val boardSizeRaw = this.readParamAfter(param) ?: return InfiniteChessBoardDimensions
    val (rows, cols) = boardSizeRaw.split(",").map(String::trim)
    return FiniteChessBoardDimensions(rows.toInt(), cols.toInt())
}

private fun Array<String>.readCoordinate(param: String): ChessBoard.Coordinate {
    val boardSizeRaw = this.readParamAfter(param) ?: throw Error("$param was not provided")
    val (x, y) = boardSizeRaw.split(",").map(String::trim)
    return ChessBoard.Coordinate(x.toInt(), y.toInt())
}

private fun Array<String>.readParamAfter(param: String): String? {
    val i = this.indexOf(param)
    if (i == -1) return null
    return this.getOrElse(i + 1) { throw Error("No value given after $param") }
}
