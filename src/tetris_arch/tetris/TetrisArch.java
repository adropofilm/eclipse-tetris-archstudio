package tetris_arch.tetris;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import tetris_arch.IBoardPanel;
import tetris_arch.IClock;
import tetris_arch.ISidePanel;
import tetris_arch.ITetris;

public class TetrisArch extends AbstractMyxSimpleBrick implements ITetris
{
    public static final IMyxName msg_ITetris = MyxUtils.createName("tetris_arch.ITetris");
    public static final IMyxName msg_IClock = MyxUtils.createName("tetris_arch.IClock");
    public static final IMyxName msg_ISidePanel = MyxUtils.createName("tetris_arch.ISidePanel");
    public static final IMyxName msg_IBoardPanel = MyxUtils.createName("tetris_arch.IBoardPanel");

    public IClock OUT_IClock;
    public ISidePanel OUT_ISidePanel;
    public IBoardPanel OUT_IBoardPanel;

	private ITetrisImp _imp;

    public TetrisArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected ITetrisImp getImplementation(){
        try{
			return new TetrisImp();    
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void init(){
        _imp.init();
    }
    
    public void begin(){
        OUT_IClock = (IClock) MyxUtils.getFirstRequiredServiceObject(this,msg_IClock);
        if (OUT_IClock == null){
 			System.err.println("Error: Interface tetris_arch.IClock returned null");
			return;       
        }
        OUT_ISidePanel = (ISidePanel) MyxUtils.getFirstRequiredServiceObject(this,msg_ISidePanel);
        if (OUT_ISidePanel == null){
 			System.err.println("Error: Interface tetris_arch.ISidePanel returned null");
			return;       
        }
        OUT_IBoardPanel = (IBoardPanel) MyxUtils.getFirstRequiredServiceObject(this,msg_IBoardPanel);
        if (OUT_IBoardPanel == null){
 			System.err.println("Error: Interface tetris_arch.IBoardPanel returned null");
			return;       
        }
        _imp.begin();
    }
    
    public void end(){
        _imp.end();
    }
    
    public void destroy(){
        _imp.destroy();
    }
    
	public Object getServiceObject(IMyxName arg0) {
		if (arg0.equals(msg_ITetris)){
			return this;
		}        
		return null;
	}
  
    //To be imported: TileType
    public void startGame ()   {
		_imp.startGame();
    }    
    public boolean isPaused ()   {
		return _imp.isPaused();
    }    
    public boolean isGameOver ()   {
		return _imp.isGameOver();
    }    
    public boolean isNewGame ()   {
		return _imp.isNewGame();
    }    
    public TileType getPieceType ()   {
		return _imp.getPieceType();
    }    
    public int getPieceCol ()   {
		return _imp.getPieceCol();
    }    
    public int getPieceRow ()   {
		return _imp.getPieceRow();
    }    
    public int getPieceRotation ()   {
		return _imp.getPieceRotation();
    }    
}