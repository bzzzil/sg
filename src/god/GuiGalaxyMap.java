package god;

import gui.JMap;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import util.Coordinate;
import util.StarArray;
import world.*;

public class GuiGalaxyMap extends JMap implements MouseListener, ComponentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 845167600466270803L;
	
	private static final Color rulerColor = new Color(30,30,30);
	private static final Color textColor = new Color(200,200,200);

	private int mouseOverStar = -1;
	private Image buffer;
	private boolean showNames = false, showIds = false, showRulers = false;

	public GuiGalaxyMap() {
		addComponentListener(this);
		addMouseListener(this);
		setFont(new Font("Dialog", Font.PLAIN, 10));
		setPreferredSize(new Dimension(300,200));
	}

	/**
	 * Paint window
	 */
	public void paint(Graphics g) {
		Dimension size = getSize();

		// Double buffering
		Graphics secondBuffer = g;
		g = buffer.getGraphics();

		if (Galaxy.getInstance() == null || Galaxy.getStars() == null) {
			g.fillRect(0, 0, size.width, size.height);
		} else {
			g.setColor(Color.black);
			g.fillRect(0, 0, size.width, size.height);

			// Draw stars
			for (int i = 0; i < Galaxy.getStars().size(); i++) {
				Galaxy.getStars().get(i).draw(g, 
						this.scale, this.x, this.y, 
						this.showNames, this.showIds, this.mouseOverStar==i);
			}
			
			// Draw rulers
			if (showRulers) {
				drawRulers(g, size);
			}
			
			// Draw info 
			g.setColor(textColor);
			g.drawString(x + "," + y + "x" + this.scale, 0, 10);
		}

		secondBuffer.drawImage(buffer, 0, 0, null);
	}
	
	public void drawRulers(Graphics g, Dimension size) {
		g.setColor(this.rulerColor);
		int rulerX = (int)(Math.ceil(-this.x/this.scale/100)*100); 
		do {
			int rulerX_x = this.x + (int)(rulerX * this.scale);
			g.drawLine(rulerX_x, 0, rulerX_x, size.height);
			rulerX += 100;
		} while (rulerX * this.scale < size.width);

		int rulerY = (int)(Math.ceil(-this.y/this.scale/100)*100); 
		do {
			int rulerY_y = this.y + (int)(rulerY * this.scale);
			g.drawLine(0, rulerY_y, size.width, rulerY_y);
			rulerY += 100;
		} while (rulerY * this.scale < size.height);
	}

	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Handle mouse movement
	 */
	public void mouseMoved(MouseEvent me) {
		StarArray stars = Galaxy.getStars();

		if (stars != null) {
			// Mouse coordinates as space location
			Coordinate mouseCoordinate = new Coordinate(
					(int)((me.getX() - this.x) / this.scale),
					(int)((me.getY() - this.y) / this.scale));

			// Find nearest to mouse star
			int nearestStar = Galaxy.getStars().getNearest(mouseCoordinate);
			
			if (nearestStar<0)
				return;
			
			double nearestStarDistance = Galaxy.getStars().get(nearestStar).getDistance(mouseCoordinate) * this.scale;

			if (this.mouseOverStar == -1 && nearestStarDistance <= this.scale + 7) {
				this.mouseOverStar = nearestStar;
				this.repaint();
			} else if (this.mouseOverStar != -1 && nearestStarDistance > this.scale + 7) {
				this.mouseOverStar = -1;
				this.repaint();
			}
		}
	}

	/**
	 * Handle mouse clicks
	 */
	public void mouseClicked(MouseEvent me) {
		if (this.mouseOverStar != -1)
		{
			GuiStarInfoDialog d = new GuiStarInfoDialog(
					(JFrame)this.getTopLevelAncestor(), 
					Galaxy.getStars().get(this.mouseOverStar));
			d.setVisible(true);
		}
	}

	/**
	 * Handle mouse enter event
	 */
	public void mouseEntered(MouseEvent me) {
	}

	/**
	 * Handle mouse leave event
	 */
	public void mouseExited(MouseEvent me) {
	}

	public void componentResized(ComponentEvent ce) {
		Dimension d = getSize();
		if (d.width>0 && d.height>0)
		{
			buffer = createImage(d.width, d.height);
			this.repaint();
		}
	}

	public void componentHidden(ComponentEvent arg0) {

	}

	public void componentMoved(ComponentEvent arg0) {

	}

	public void componentShown(ComponentEvent arg0) {

	}

	public boolean isShowNames() {
		return showNames;
	}

	public void setShowNames(boolean showNames) {
		this.showNames = showNames;
	}

	public boolean isShowIds() {
		return showIds;
	}

	public void setShowIds(boolean showIds) {
		this.showIds = showIds;
	}

	public boolean isShowRulers() {
		return showRulers;
	}

	public void setShowRulers(boolean showRulers) {
		this.showRulers = showRulers;
	}
}
