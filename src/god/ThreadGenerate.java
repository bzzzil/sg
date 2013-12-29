package god;

import world.Galaxy;

public class ThreadGenerate extends Thread {
	private GodFrame guiFrame;
	
	public ThreadGenerate(GodFrame guiFrame)
	{
		this.guiFrame = guiFrame;
	}
	
	public void run()
	{
		//this.guiFrame.setEnabled(false);
		
		Galaxy.getInstance().setSeed(Galaxy.getGenerator().nextInt());
		Galaxy.getInstance().create();

		//this.guiFrame.setEnabled(true);
		this.guiFrame.map.repaint();
	}
}
