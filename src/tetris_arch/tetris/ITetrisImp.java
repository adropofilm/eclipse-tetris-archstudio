package tetris_arch.tetris;


import tetris_arch.tetris.TetrisArch;
import tetris_arch.tetris.TileType;

public interface ITetrisImp 
{

	/*
	  Getter and Setter of architecture reference
	*/
    public void setArch (TetrisArch arch);
	public TetrisArch getArch();
	
	/*
  	  Myx Lifecycle Methods: these methods are called automatically by the framework
  	  as the bricks are created, attached, detached, and destroyed respectively.
	*/	
	public void init();	
	public void begin();
	public void end();
	public void destroy();

	/*
  	  Implementation primitives required by the architecture
	*/
  
    //To be imported: TileType
    public void startGame ()  ;        
    public boolean isPaused ()  ;        
    public boolean isGameOver ()  ;        
    public boolean isNewGame ()  ;        
    public TileType getPieceType ()  ;        
    public int getPieceCol ()  ;        
    public int getPieceRow ()  ;        
    public int getPieceRotation ()  ;        
}