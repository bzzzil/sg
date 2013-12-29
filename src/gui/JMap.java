package gui;

import java.awt.event.*;
import javax.swing.JComponent;


public class JMap extends JComponent 
	implements MouseMotionListener, MouseWheelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7642872258252222012L;
	protected int x, y;
	protected int click_x, click_y;
	protected double scale = 1;

	public JMap()
	{
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}

	/**
	 * Handle mouse wheel actions
	 */
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		// Change zoom level
		double old_scale = this.scale;
		if (mwe.getWheelRotation()>0)
			this.scale /= 2;
		else
			this.scale *= 2;
			
		if (this.scale < 0.1)
			this.scale = 0.1;
		if (this.scale > 16)
			this.scale = 16;

		// Change location
		x = (int)((x - mwe.getX()) * this.scale / old_scale + mwe.getX());
		y = (int)((y - mwe.getY()) * this.scale / old_scale + mwe.getY());
		
		repaint();
	}

	/**
	 * Handle mouse button pressing
	 */
	public void mousePressed(MouseEvent me) {
		this.click_x = me.getX();
		this.click_y = me.getY();
	}

	/**
	 * Handle mouse button releasing
	 */
	public void mouseReleased(MouseEvent me) {
		this.click_x = 0;
		this.click_y = 0;
	}

	/**
	 * Handle mouse drag action
	 */
	public void mouseDragged(MouseEvent me) {
		this.x += me.getX() - this.click_x;
		this.y += me.getY() - this.click_y;
		
		this.click_x = me.getX();
		this.click_y = me.getY();

		repaint();
	}

	/**
	 * Handle mouse movement
	 */
	public void mouseMoved(MouseEvent me) {
		
	}
}
