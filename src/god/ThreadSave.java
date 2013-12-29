package god;

import world.Galaxy;

public class ThreadSave extends Thread {
	private GodFrame guiFrame;
	
	public ThreadSave(GodFrame guiFrame)
	{
		this.guiFrame = guiFrame;
	}
	
	public void run()
	{
		//this.guiFrame.setEnabled(false);
		
		Galaxy.getInstance().save();

		//this.guiFrame.setEnabled(true);
		this.guiFrame.map.repaint();
	}
}
