package tetris_arch.board;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import tetris_arch.IBoardPanel;
import tetris_arch.ITetris;

import tetris_arch.tetris.TetrisImp;
import tetris_arch.tetris.TileType;

public class BoardPanelArch extends AbstractMyxSimpleBrick implements IBoardPanel
{
    public static final IMyxName msg_ITetris = MyxUtils.createName("tetris_arch.ITetris");
    public static final IMyxName msg_IBoardPanel = MyxUtils.createName("tetris_arch.IBoardPanel");

    public ITetris OUT_ITetris;

	private IBoardPanelImp _imp;

    public BoardPanelArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected IBoardPanelImp getImplementation(){
        try{
			return new BoardPanelImp();    
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void init(){
        _imp.init();
    }
    
    public void begin(){
        OUT_ITetris = (ITetris) MyxUtils.getFirstRequiredServiceObject(this,msg_ITetris);
        if (OUT_ITetris == null){
 			System.err.println("Error: Interface tetris_arch.ITetris returned null");
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
		if (arg0.equals(msg_IBoardPanel)){
			return this;
		}        
		return null;
	}
  
    //To be imported: TetrisImp,TileType
    public void setBoardPanel (TetrisImp tetris)   {
		_imp.setBoardPanel(tetris);
    }    
    public boolean isValidAndEmpty (TileType type,int x,int y,int rotation)   {
		return _imp.isValidAndEmpty(type,x,y,rotation);
    }    
    public void addPiece (TileType type,int x,int y,int rotation)   {
		_imp.addPiece(type,x,y,rotation);
    }    
    public int checkLines ()   {
		return _imp.checkLines();
    }    
    public void clear ()   {
		_imp.clear();
    }    
    public void repaint ()   {
		_imp.repaint();
    }    
    public int getColCount ()   {
		return _imp.getColCount();
    }    
    public int getRowCount ()   {
		return _imp.getRowCount();
    }    
}