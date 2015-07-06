import java.awt.Point;
import java.util.ArrayList;

public class GameLogic {
	public static final int ROWS = 6;
	public static final int COLS = 6;
	
	public static final int BLACK = -1;
	public static final int WHITE = 1;
	public static final int BLANK = 0;
	public static final int BOTH = 2;
	
	private int[][] board;
	private int turn;
	
	public GameLogic() {
		turn = WHITE;
		createBoard();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int getSpace(int row, int col) {
		return board[row][col];
	}
	
	public void makeMove(int row, int col) {
		board[row][col] = turn;
		flipPieces(getPossibleFlips(row, col));
		turn *= -1;
	}
	
	public void changeTurn() {
		turn *= -1;
	}
	
	public boolean isValidMove(int row, int col) {
		return isOnBoard(row, col) && board[row][col] == BLANK && getPossibleFlips(row, col).size() >= 1;
	}
	
	public boolean isGameOver() {
		return isBoardFull() || !isMovesAvailable(BOTH);
	}
	
	public int getWinner() {
		int blacks = getScore().x;
		int whites = getScore().y;
		int winner = BLANK;
		if(blacks > whites) {
			winner = BLACK;
		}
		else if(whites > blacks) {
			winner = WHITE;
		}
		else {
			winner = BOTH;
		}
		return winner;	
	}
	
	public boolean isMovesAvailable(int player) {
		ArrayList<Point> currentPlayerMoves = new ArrayList<Point>();
		ArrayList<Point> nextPlayerMoves = new ArrayList<Point>();
		
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				if(board[r][c] == BLANK) {
					currentPlayerMoves.addAll(getPossibleFlips(r, c));
					turn *= -1;
					nextPlayerMoves.addAll(getPossibleFlips(r, c));
					turn *= -1;
				}
			}
		}
		if(player == BOTH) {
			return !(currentPlayerMoves.size() == 0 && nextPlayerMoves.size() == 0);
		}
		else {
			return currentPlayerMoves.size() != 0;
		}			
		
	}
	
	public Point getScore() {
		int blacks = 0;
		int whites = 0;
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				int piece = board[r][c];
				if(piece == BLACK) {
					blacks++;
				}
				else if(piece == WHITE) {
					whites++;
				}
			}
		}
		return new Point(blacks, whites);
	}
	
	public ArrayList<Point> getPossibleFlips(int startRow, int startCol) {
		ArrayList<Point> result = new ArrayList<Point>();
		
		for(int r = -1; r <= 1; r++) {
			for(int c = -1; c <= 1; c++) {
				result.addAll(getPossibleFlipsDir(startRow + r, startCol + c, r, c));
			}
		}
		
		return result;
	}
	
	private ArrayList<Point> getPossibleFlipsDir(int row, int col, int rowIncr, int colIncr) {
		ArrayList<Point> temp = new ArrayList<Point>();
		if(isOnBoard(row, col) && board[row][col] == turn*-1) {
			temp.add(new Point(row, col));
			row += rowIncr;
			col += colIncr;
			while(isOnBoard(row, col)) {
				if(board[row][col] == turn) {
					return temp;
				}
				else if(board[row][col] == turn * -1) {
					temp.add(new Point(row, col));
					row += rowIncr;
					col += colIncr;
				}
				else {
					return new ArrayList<Point>();
				}
			}
			return new ArrayList<Point>();
		}
		else {
			return temp;
		}
	}
	
	private boolean isBoardFull() {
		boolean full = true;
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				if(board[r][c] == BLANK) {
					full = false;
				}
			}
		}
		return full;
	}
	
	private void flipPieces(ArrayList<Point> piecesToFlip) {
		for(int i = 0; i < piecesToFlip.size(); i++) {
			Point piece = piecesToFlip.get(i);
			board[piece.x][piece.y] = turn;
		}
	}
	
	private boolean isOnBoard(int row, int col) {
		return 0 <= row && row < ROWS && 0 <= col && col < COLS;
	}
	
	private void createBoard() {
		board = new int[ROWS][COLS];

		int leftCorner = BLACK;
		for(int r = ROWS/2 - 1; r < ROWS/2 + 1; r++) {
			for(int c = COLS/2 - 1; c < COLS/2 + 1; c++) {
				board[r][c] = leftCorner;
				leftCorner *= -1;
			}
			leftCorner *= -1;
		}
	}
}
