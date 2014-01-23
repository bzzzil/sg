package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;


public class JMap extends JComponent 
	implements MouseMotionListener, MouseWheelListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7642872258252222012L;
    public static final double SCALE_MAX = 16;
    public static final double SCALE_MIN = 0.05;
    protected int x, y;
	private int click_x, click_y;
	protected double scale = 1;
    protected Rectangle boundsRect = new Rectangle();
    protected Rectangle scaledBoundsRect = new Rectangle();

	protected JMap() {
		addMouseMotionListener(this);
		addMouseWheelListener(this);
        updateBoundsRectangle();
	}

    protected void updateBoundsRectangle() {
        scaledBoundsRect.x = (int)(boundsRect.x * scale);
        scaledBoundsRect.width = (int)(boundsRect.width * scale);

        scaledBoundsRect.y = (int)(boundsRect.y * scale);
        scaledBoundsRect.height = (int)(boundsRect.height * scale);
    }

    public void setBoundsRectangle(Rectangle boundsRect) {
        this.boundsRect = boundsRect;

        updateBoundsRectangle();
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
			
		if (this.scale < SCALE_MIN)
			this.scale = SCALE_MIN;
		if (this.scale > SCALE_MAX)
			this.scale = SCALE_MAX;

		// Change location
		x = (int)((x - mwe.getX()) * this.scale / old_scale + mwe.getX());
		y = (int)((y - mwe.getY()) * this.scale / old_scale + mwe.getY());

        updateBoundsRectangle();
        checkBounds();
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

        checkBounds();

		repaint();
	}

	/**
	 * Handle mouse movement
	 */
	public void mouseMoved(MouseEvent me) {
		
	}

    /**
     * Check bounds of viewport comparing to galaxy bound
     * Do not allow moving offsets (x and y) far away from map borders
     */
    protected void checkBounds(){
        if (this.x < - (scaledBoundsRect.x + scaledBoundsRect.width))
            this.x = - (scaledBoundsRect.x + scaledBoundsRect.width);
        if (this.x > - scaledBoundsRect.x + this.getWidth())
            this.x = - scaledBoundsRect.x + this.getWidth();
        if (this.y < - (scaledBoundsRect.y + scaledBoundsRect.height))
            this.y = - (scaledBoundsRect.y + scaledBoundsRect.height);
        if (this.y > - scaledBoundsRect.y + this.getHeight())
            this.y = - scaledBoundsRect.y + this.getHeight();
    }
}
