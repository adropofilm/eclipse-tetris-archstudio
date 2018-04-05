package tetris_arch;

import tetris_arch.tetris.TetrisImp;
import tetris_arch.tetris.TileType;

public interface IBoardPanel {
	public void setBoardPanel(TetrisImp tetris);
	public boolean isValidAndEmpty(TileType type, int x, int y, int rotation);
	public void addPiece(TileType type, int x, int y, int rotation);
	public int checkLines();
	public void clear();
	public void repaint();
	public int getColCount();
	public int getRowCount();
}
