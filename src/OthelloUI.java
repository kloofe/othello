import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OthelloUI implements MouseListener {

	private GameLogic gameState;
	private GameBoard board;
	private JLabel score;
	private double width;
	private double height;
	
	public OthelloUI() {
		JFrame frame = new JFrame();
		frame.setTitle("Othello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		
		width = 500;
		height = 500;
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 500));
				
		gameState = new GameLogic();
		board = new GameBoard(width, height, gameState);
		board.setPreferredSize(new Dimension(500, 500));
		
		panel.add(board);
		panel.addMouseListener(this);

		score = new JLabel("");
		score.setPreferredSize(new Dimension(500, 50));
		score.setHorizontalAlignment(SwingConstants.CENTER);
		score.setFont(new Font("Serif", Font.PLAIN, 25));
		updateScore();
		
		frame.add(score, BorderLayout.NORTH);
		
		frame.add(panel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	private Point getCell(int x, int y) {
		int row = (int) Math.round(y) / (int) Math.round(height/GameLogic.ROWS);
		int col = (int) Math.round(x) / (int) Math.round(width/GameLogic.COLS);
		return new Point(row, col);
	}
	
	private void playerTurn(Point cell) {
		int row = cell.x;
		int col = cell.y;
		if(gameState.isValidMove(row, col)) {
			gameState.makeMove(row, col);
			board.drawBoard(width, height, gameState);
		}
		if(!gameState.isMovesAvailable(gameState.getTurn())) {
			gameState.changeTurn();
			updateScore();
			board.drawBoard(width, height, gameState);
			if(!gameState.isMovesAvailable(gameState.getTurn())) {
				runGameover();
			}
		}
	}
	
	private void runGameover() {
		int winner = gameState.getWinner();
		String winnerStr = "";
		if(winner == GameLogic.BLACK) {
			winnerStr = "Black";
		}
		else if(winner == GameLogic.WHITE) {
			winnerStr = "White";
		}
		else {
			winnerStr = "Both of you!";
		}
		Point scores = gameState.getScore();
		int blacks = scores.x;
		int whites = scores.y;
		updateScore();
		int input = JOptionPane.showConfirmDialog(null, "The winner is... " + winnerStr + "! With a score of " + whites + "w vs " + blacks + "b.\nWould you like to play again?",
				"Game Over", JOptionPane.YES_NO_OPTION);
		if(input == JOptionPane.YES_OPTION) {
			restart();
		}
		else {
			System.exit(0);
		}
	}
	
	private void restart() {
		gameState = new GameLogic();
		board.drawBoard(width, height, gameState);
	}
	
	private void updateScore() {
		int blacks = gameState.getScore().x;
		int whites = gameState.getScore().y;
		String turn = "";
		if(gameState.getTurn() == GameLogic.BLACK) {
			turn = "Black";
		}
		else {
			turn = "White";
		}
		score.setText("White: " + whites + "      Black: " + blacks + "      Turn: " + turn);
	}
	
	public void mousePressed(MouseEvent e) {
		Point cell = getCell(e.getX(), e.getY());
		if(!gameState.isGameOver()) {
			playerTurn(cell);
			updateScore();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
			
	public void mouseClicked(MouseEvent e) {
		
	}
	

} 