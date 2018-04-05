package tetris_arch.side;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import tetris_arch.ISidePanel;
import tetris_arch.ITetris;

import tetris_arch.tetris.TetrisImp;

public class SidePanelArch extends AbstractMyxSimpleBrick implements ISidePanel
{
    public static final IMyxName msg_ISidePanel = MyxUtils.createName("tetris_arch.ISidePanel");
    public static final IMyxName msg_ITetris = MyxUtils.createName("tetris_arch.ITetris");

    public ITetris OUT_ITetris;

	private ISidePanelImp _imp;

    public SidePanelArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected ISidePanelImp getImplementation(){
        try{
			return new SidePanelImp();    
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
		if (arg0.equals(msg_ISidePanel)){
			return this;
		}        
		return null;
	}
  
    //To be imported: TetrisImp
    public void setSidePanel (TetrisImp tetris)   {
		_imp.setSidePanel(tetris);
    }    
    public void repaint ()   {
		_imp.repaint();
    }    
}