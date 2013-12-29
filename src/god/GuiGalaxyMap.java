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
	/**
	 * Rulers coordinates text color
	 */
	private static final Color rulerTextColor = new Color(60,60,60);

	/**
	 * Ruler 100 line properties
	 */
	private static final Color ruler100Color = new Color(30,30,30);
	private static final float dash1[] = {10.0f};
	private static final BasicStroke ruler100Stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	/**
	 * Ruler 1000 line properties
	 */
	private static final Color ruler1000Color = new Color(30,30,30);
	private static final BasicStroke ruler1000Stroke = new BasicStroke(2.0f);

	/**
	 * Static text color
	 */
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
		Graphics2D g2 = (Graphics2D)g;

		if (Galaxy.getInstance() == null || Galaxy.getStars() == null) {
			g2.fillRect(0, 0, size.width, size.height);
		} else {
			g2.setColor(Color.black);
			g2.fillRect(0, 0, size.width, size.height);

			// Draw rulers
			if (isShowRulers()) {
				if (this.scale>=0.5) {
					drawRulers(g2, size, 100, GuiGalaxyMap.ruler100Color, GuiGalaxyMap.ruler100Stroke);
				}
				drawRulers(g2, size, 1000, GuiGalaxyMap.ruler1000Color, GuiGalaxyMap.ruler1000Stroke);
			}
			
			// Draw stars
			for (int i = 0; i < Galaxy.getStars().size(); i++) {
				Galaxy.getStars().get(i).draw(g2,
						this.scale, this.x, this.y, 
						this.isShowNames(), this.isShowIds(), this.mouseOverStar==i);
			}
			
			// Draw info 
			g2.setColor(GuiGalaxyMap.textColor);
			g2.drawString(x + "," + y + "x" + this.scale, 0, 10);
		}

		secondBuffer.drawImage(buffer, 0, 0, null);
	}

    /**
     * Draw vertical and horizontal rulers with given step
     * @param g2 draw object
     * @param size size of viewport
     * @param step step for rulers
     * @param color lines color
     * @param stroke lines stroking style
     */
	public void drawRulers(Graphics2D g2, Dimension size, int step, Color color, BasicStroke stroke) {
		g2.setStroke(stroke);
		int rulerX = (int)(Math.ceil(-this.x/this.scale/step)*step);
		int rulerX_x;
		do {
			rulerX_x = this.x + (int)(rulerX * this.scale);
			g2.setColor(color);
			g2.drawLine(rulerX_x, 0, rulerX_x, size.height);
			g2.setColor(GuiGalaxyMap.rulerTextColor);
			g2.drawString(rulerX + "'", rulerX_x + 5, 10);
			rulerX += step;
		} while (rulerX_x <= size.width);

		int rulerY = (int)(Math.ceil(-this.y/this.scale/step)*step);
		int rulerY_y;
		do {
			rulerY_y = this.y + (int)(rulerY * this.scale);
			g2.setColor(color);
			g2.drawLine(0, rulerY_y, size.width, rulerY_y);
			g2.setColor(GuiGalaxyMap.rulerTextColor);
			g2.drawString(rulerY + "'", 5 , rulerY_y + 15);
			rulerY += step;
		} while (rulerY_y <= size.height);
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
			
			if (nearestStar<0) {
				return;
            }
			
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
