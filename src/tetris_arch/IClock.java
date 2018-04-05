package tetris_arch;

public interface IClock {
	public void setClock(float cyclesPerSecond);
	public void setCyclesPerSecond(float cyclesPerSecond);
	public void reset();
	public void update();
	public void setPaused(boolean paused);
	public boolean isPaused();
	public boolean hasElapsedCycle();
	public boolean peekElapsedCycle();
}
