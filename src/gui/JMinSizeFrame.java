package gui;

import javax.swing.*;
import java.awt.event.*;

public class JMinSizeFrame extends JFrame implements ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4104446899264410818L;
	private int minHeight = -1;
	private int minWidth  = -1;

	public JMinSizeFrame() {
		addComponentListener(this);
	}
	
	public void setMinimumSize(int width, int height)
	{
		minWidth = width;
		minHeight = height;
	}

	public void setMinimumWidth(int width)
	{
		minWidth = width;
	}

	public void setMinimumHeight(int height)
	{
		minHeight = height;
	}

	public void componentResized(ComponentEvent e) {
		int width = getWidth();
		int height = getHeight();
		// we check if either the width
		// or the height are below minimum
		boolean resize = false;
		if (width < minWidth && minWidth!=-1) {
			resize = true;
			width = minWidth;
		}
		if (height < minHeight && minHeight!=-1) {
			resize = true;
			height = minHeight;
		}
		if (resize) {
			setSize(width, height);
		}
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}
}
