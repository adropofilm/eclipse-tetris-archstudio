package tetris_arch.board;

import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import tetris_arch.tetris.TileType;
import tetris_arch.tetris.TetrisImp;

public class BoardPanelImp extends JPanel implements IBoardPanelImp
{
	private BoardPanelArch _arch;
	
	private static final long serialVersionUID = 5055679736784226108L;
	public static final int COLOR_MIN = 35;
	public static final int COLOR_MAX = 255 - COLOR_MIN;
	private static final int BORDER_WIDTH = 5;
	public static final int COL_COUNT = 10;
	private static final int VISIBLE_ROW_COUNT = 20;
	private static final int HIDDEN_ROW_COUNT = 2;
	public static final int ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT;
	public static final int TILE_SIZE = 24;
	public static final int SHADE_WIDTH = 4;
	private static final int CENTER_X = COL_COUNT * TILE_SIZE / 2;
	private static final int CENTER_Y = VISIBLE_ROW_COUNT * TILE_SIZE / 2;
	public static final int PANEL_WIDTH = COL_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	public static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 16);
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);
	private TetrisImp tetris;
	private TileType[][] tiles;


    public BoardPanelImp (){
    }

	public void setArch(BoardPanelArch arch){
		_arch = arch;
	}
	public BoardPanelArch getArch(){
		return _arch;
	}

	/*
  	  Myx Lifecycle Methods: these methods are called automatically by the framework
  	  as the bricks are created, attached, detached, and destroyed respectively.
	*/	
	public void init(){
	    //TODO Auto-generated method stub
	}
	public void begin(){
		//TODO Auto-generated method stub
	}
	public void end(){
		//TODO Auto-generated method stub
	}
	public void destroy(){
		//TODO Auto-generated method stub
	}

	@Override 
	public void repaint() {

		super.repaint();
		
	}

	@Override
	public void setBoardPanel(TetrisImp tetris) {
		// TODO Auto-generated method stub
		this.tetris = tetris;
	 	this.tiles = new TileType[ROW_COUNT][COL_COUNT];
	 	tetris.add(this, BorderLayout.CENTER);
	
	 	setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	 	setBackground(Color.BLACK);
	}

	@Override
	public boolean isValidAndEmpty(TileType type, int x, int y, int rotation) {
		// TODO Auto-generated method stub
		//Ensure the piece is in a valid column.
		if(x < -type.getLeftInset(rotation) || x + type.getDimension() - type.getRightInset(rotation) >= COL_COUNT) {
			return false;
		}
		
		//Ensure the piece is in a valid row.
		if(y < -type.getTopInset(rotation) || y + type.getDimension() - type.getBottomInset(rotation) >= ROW_COUNT) {
			return false;
		}
		
		/*
		 * Loop through every tile in the piece and see if it conflicts with an existing tile.
		 * 
		 * Note: It's fine to do this even though it allows for wrapping because we've already
		 * checked to make sure the piece is in a valid location.
		 */
		for(int col = 0; col < type.getDimension(); col++) {
			for(int row = 0; row < type.getDimension(); row++) {
				if(type.isTile(col, row, rotation) && isOccupied(x + col, y + row)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void addPiece(TileType type, int x, int y, int rotation) {
		for(int col = 0; col < type.getDimension(); col++) {
			for(int row = 0; row < type.getDimension(); row++) {
				if(type.isTile(col, row, rotation)) {
					setTile(col + x, row + y, type);
				}
			}
		}
	}

	@Override
	public int checkLines() {
		// TODO Auto-generated method stub
		int completedLines = 0;
		
		/*
		 * Here we loop through every line and check it to see if
		 * it's been cleared or not. If it has, we increment the
		 * number of completed lines and check the next row.
		 * 
		 * The checkLine function handles clearing the line and
		 * shifting the rest of the board down for us.
		 */
		for(int row = 0; row < ROW_COUNT; row++) {
			if(checkLine(row)) {
				completedLines++;
			}
		}
		return completedLines;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		for(int i = 0; i < ROW_COUNT; i++) {
			for(int j = 0; j < COL_COUNT; j++) {
				tiles[i][j] = null;
			}
		}
	}

	/*
  	  Implementation primitives required by the architecture
	*/
  
	private boolean isOccupied(int x, int y) {
		return tiles[y][x] != null;
	}
	
	private void setTile(int  x, int y, TileType type) {
		tiles[y][x] = type;
	}
	private boolean checkLine(int line) {
		/*
		 * Iterate through every column in this row. If any of them are
		 * empty, then the row is not full.
		 */
		for(int col = 0; col < COL_COUNT; col++) {
			if(!isOccupied(col, line)) {
				return false;
			}
		}
		
		/*
		 * Since the line is filled, we need to 'remove' it from the game.
		 * To do this, we simply shift every row above it down by one.
		 */
		for(int row = line - 1; row >= 0; row--) {
			for(int col = 0; col < COL_COUNT; col++) {
				setTile(col, row + 1, getTile(col, row));
			}
		}
		return true;
	}
	
	private TileType getTile(int x, int y) {
		return tiles[y][x];
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//This helps simplify the positioning of things.
		g.translate(BORDER_WIDTH, BORDER_WIDTH);
		
		/*
		 * Draw the board differently depending on the current game state.
		 */

		if (_arch.OUT_ITetris.isPaused()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);
			String msg = "PAUSED";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, CENTER_Y);
		} else if(_arch.OUT_ITetris.isNewGame() || _arch.OUT_ITetris.isGameOver()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);
			
			/*
			 * Because both the game over and new game screens are nearly identical,
			 * we can handle them together and just use a ternary operator to change
			 * the messages that are displayed.
			 */
			String msg = _arch.OUT_ITetris.isNewGame() ? "TETRIS" : "GAME OVER";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 150);
			g.setFont(SMALL_FONT);
			msg = "Press Enter to Play" + (_arch.OUT_ITetris.isNewGame() ? "" : " Again");
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 300);
		} else {
			

			for(int x = 0; x < COL_COUNT; x++) {
				for(int y = HIDDEN_ROW_COUNT; y < ROW_COUNT; y++) {
					TileType tile = getTile(x, y);
					if(tile != null) {
						drawTile(tile, x * TILE_SIZE, (y - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}
			
			TileType type = _arch.OUT_ITetris.getPieceType();
			int pieceCol = _arch.OUT_ITetris.getPieceCol();
			int pieceRow = _arch.OUT_ITetris.getPieceRow();
			int rotation = _arch.OUT_ITetris.getPieceRotation();
			

			for(int col = 0; col < type.getDimension(); col++) {
				for(int row = 0; row < type.getDimension(); row++) {
					if(pieceRow + row >= 2 && type.isTile(col, row, rotation)) {
						drawTile(type, (pieceCol + col) * TILE_SIZE, (pieceRow + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}
			
			Color base = type.getBaseColor();
			base = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
			for(int lowest = pieceRow; lowest < ROW_COUNT; lowest++) {

				if(isValidAndEmpty(type, pieceCol, lowest, rotation)) {					
					continue;
				}
				
				lowest--;
				
				for(int col = 0; col < type.getDimension(); col++) {
					for(int row = 0; row < type.getDimension(); row++) {
						if(lowest + row >= 2 && type.isTile(col, row, rotation)) {
							drawTile(base, base.brighter(), base.darker(), (pieceCol + col) * TILE_SIZE, (lowest + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
						}
					}
				}
				
				break;
			}
			
			g.setColor(Color.DARK_GRAY);
			for(int x = 0; x < COL_COUNT; x++) {
				for(int y = 0; y < VISIBLE_ROW_COUNT; y++) {
					g.drawLine(0, y * TILE_SIZE, COL_COUNT * TILE_SIZE, y * TILE_SIZE);
					g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, VISIBLE_ROW_COUNT * TILE_SIZE);
				}
			}
		}

		g.setColor(Color.WHITE);
		g.drawRect(0, 0, TILE_SIZE * COL_COUNT, TILE_SIZE * VISIBLE_ROW_COUNT);
	}
	private void drawTile(TileType type, int x, int y, Graphics g) {
		drawTile(type.getBaseColor(), type.getLightColor(), type.getDarkColor(), x, y, g);
	}
	
	private void drawTile(Color base, Color light, Color dark, int x, int y, Graphics g) {
		
		g.setColor(base);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

		g.setColor(dark);
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);
		
		g.setColor(light);
		for(int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}

	@Override
	public int getColCount() {
		// TODO Auto-generated method stub
		return COL_COUNT;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return ROW_COUNT;
	}
}