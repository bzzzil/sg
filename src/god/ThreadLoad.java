package god;

import world.Galaxy;

class ThreadLoad extends Thread {
    private final GodFrame guiFrame;

    public ThreadLoad(GodFrame guiFrame)
    {
        this.guiFrame = guiFrame;
    }

    public void run()
    {
        //this.guiFrame.setEnabled(false);

        Galaxy.getInstance().load();

        //this.guiFrame.setEnabled(true);
        this.guiFrame.map.repaint();
    }
}
