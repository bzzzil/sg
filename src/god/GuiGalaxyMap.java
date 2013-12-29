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

	private int mouseOverStar = -1;
	private Image buffer;
	private boolean showNames = false, showIds = false; 

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
			StarArray stars = Galaxy.getStars();
			g.setColor(Color.black);
			g.fillRect(0, 0, size.width, size.height);

			for (int i = 0; i < stars.size(); i++) {
				stars.get(i).draw(g, scale, x, y, showNames, showIds, mouseOverStar==i);
			}
			g.drawString(
					x + "," + y + "x" + this.scale, 0,
					10);
		}


		secondBuffer.drawImage(buffer, 0, 0, null);
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
			int nearestStar = Galaxy.getStars()
					.getNearest(mouseCoordinate);
			
			if (nearestStar<0)
				return;
			
			double nearestStarDistance = Galaxy.getStars()
					.get(nearestStar).getDistance(mouseCoordinate)
					* this.scale;

			if (this.mouseOverStar == -1
					&& nearestStarDistance <= this.scale + 7) {
				this.mouseOverStar = nearestStar;
				this.repaint();
			} else if (this.mouseOverStar != -1
					&& nearestStarDistance > this.scale + 7) {
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
}
