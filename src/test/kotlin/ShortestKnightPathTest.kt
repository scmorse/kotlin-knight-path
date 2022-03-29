import org.junit.jupiter.api.Test

internal class ShortestKnightPathTest {
    @Test
    fun `should return null path when board is 2x2`() {
        val shortestPath = findShortestKnightPath(
            dimensions = FiniteChessBoardDimensions(2, 2),
            start = ChessBoard.Coordinate(0, 0),
            end = ChessBoard.Coordinate(1, 1)
        )
        assert(shortestPath == null)
    }

    @Test
    fun `should return path when board is infinite`() {
        val shortestPath = findShortestKnightPath(
            dimensions = InfiniteChessBoardDimensions,
            start = ChessBoard.Coordinate(0, 0),
            end = ChessBoard.Coordinate(1, 1)
        )
        assert(shortestPath != null && shortestPath.size == 3)
    }

    @Test
    fun `should return correct path when yDiff much greater than xDiff `() {
        val shortestPath = findShortestKnightPath(
            dimensions = FiniteChessBoardDimensions(20, 20),
            start = ChessBoard.Coordinate(0, 0),
            end = ChessBoard.Coordinate(0, 19)
        )
        assert(shortestPath != null && shortestPath.size == 12)
    }

    @Test
    fun `should return correct path when yDiff is 2x xDiff `() {
        val shortestPath = findShortestKnightPath(
            dimensions = FiniteChessBoardDimensions(20, 20),
            start = ChessBoard.Coordinate(0, 0),
            end = ChessBoard.Coordinate(9, 18)
        )
        assert(shortestPath != null && shortestPath.size == 10)
    }

    @Test
    fun `should return correct path when yDiff equal to xDiff `() {
        val shortestPath = findShortestKnightPath(
            dimensions = FiniteChessBoardDimensions(20, 20),
            start = ChessBoard.Coordinate(0, 0),
            end = ChessBoard.Coordinate(18, 18)
        )
        assert(shortestPath != null && shortestPath.size == 13)
    }
}




