import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class GameBoard extends JComponent {
	private double width;
	private double height;
	private GameLogic gameState;
	
	public GameBoard(double w, double h, GameLogic b) {
		width = w;
		height = h;
		gameState = b;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, width, height);
		g2.setColor(new Color(20, 172, 0));
		g2.fill(rect);
		g2.setColor(Color.BLACK);
		g2.draw(rect);
		
		// draws the lines for dividing the board into columns
		drawRowCol(g2, width/GameLogic.COLS, 0, width/GameLogic.COLS,
				height, width/GameLogic.COLS, 0, GameLogic.COLS);
		// draws the lines for dividing the board into rows
		drawRowCol(g2, 0, height/GameLogic.ROWS, width,
				height/GameLogic.ROWS, 0, height/GameLogic.ROWS, GameLogic.ROWS);
		
		// loops through the board and draws the pieces
		for(int r = 0; r < GameLogic.ROWS; r++) {
			for(int c = 0; c < GameLogic.COLS; c++) {
				if(gameState.getSpace(r, c) == GameLogic.BLACK) {
					drawPiece(g2, r, c, Color.BLACK);
				}
				else if(gameState.getSpace(r, c) == GameLogic.WHITE) {
					drawPiece(g2, r, c, Color.WHITE);
				}
				else if(gameState.isValidMove(r, c)) {
					if(gameState.getTurn() == GameLogic.BLACK) {
						drawPiece(g2, r, c, new Color(00, 76, 00));
					}
					else {
						drawPiece(g2, r, c, new Color(102, 178, 102));
					}
				}
			}
		}
	}
	
	private void drawRowCol(Graphics2D g2, double x1, double y1, double x2, double y2, double xIncr, double yIncr, int rowcol) {
		for(int i = 0; i < rowcol; i++) {
			Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
			g2.draw(line);
			x1 += xIncr;
			x2 += xIncr;
			y1 += yIncr;
			y2 += yIncr;
		}
	}
	
	private void drawPiece(Graphics2D g2, int row, int col, Color c) {
		double x = col * width/GameLogic.COLS;
		double y = row * height/GameLogic.ROWS;
		double w = width/GameLogic.COLS;
		double h = height/GameLogic.ROWS;
		
		Ellipse2D.Double piece = new Ellipse2D.Double(x, y, w, h);
		g2.setPaint(c);
		g2.fill(piece);
		g2.setPaint(Color.BLACK);
		g2.draw(piece);
	}
	
	public void drawBoard(double w, double h, GameLogic b) {
		width = w;
		height = h;
		gameState = b;
		
		revalidate();
		repaint();
	}
}
