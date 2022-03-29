/**
 * Determines the available space on a Chess board.
 * May represent either a chess board with finite or infinite dimensions.
 */
sealed class ChessBoardDimensions {
    abstract fun isValidCoordinate(coordinate: ChessBoard.Coordinate): Boolean
}

class FiniteChessBoardDimensions(val rows: Int, val cols: Int) : ChessBoardDimensions() {
    override fun isValidCoordinate(coordinate: ChessBoard.Coordinate): Boolean {
        return coordinate.x in 0 until cols && coordinate.y in 0 until rows
    }
}

object InfiniteChessBoardDimensions : ChessBoardDimensions() {
    override fun isValidCoordinate(coordinate: ChessBoard.Coordinate): Boolean = true
}