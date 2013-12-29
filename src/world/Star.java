package world;

import java.awt.Color;
import java.awt.Graphics2D;

import util.*;

public class Star extends DBObject {
	private int id = -1;
	private Coordinate location;
	private int temperature;
	private String name;
	private PlanetArray planets;

	public Star() 
	{
		this.location = new Coordinate();
		this.planets = new PlanetArray(this);
	}

	/**
	 * Calculate Cartesian distance between current star and give coordinate
	 * 
	 * @param anotherCoordinate
	 * @return float
	 */
	public double getDistance(Coordinate anotherCoordinate)
	{
		Coordinate myLocation = this.getLocation();
		
		return Math.sqrt(
				Math.pow(myLocation.getX() - anotherCoordinate.getX(), 2) +
				Math.pow(myLocation.getY() - anotherCoordinate.getY(), 2)
				);
	}

	/**
	 * Get star temperature
	 * 
	 * {@link} http://en.wikipedia.org/wiki/Stellar_classification
	 * 
	 * @return int - temperature in Kelvins
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * Get star temperature in visual specter for drawing
	 * 
	 * {@link} http://en.wikipedia.org/wiki/Stellar_classification
	 * 
	 * @return int - temperature in Kelvins in visual specter
	 */
    int getTemperatureAsRGB() {
		if (temperature<600)
		{
			// Y class
			// TODO correct color
			return 0xbf2901;	// Brown
		}
		else if (temperature<1300)
		{
			// T class
			// TODO correct color
			return 0xff673e;	// Brown
		}
		else if (temperature<2000)
		{
			// L class
			// TODO correct color
			return 0xfd945b;	// Brown
		}
		else if (temperature<3700)
		{
			// M class
			return 0xffcc6f;	// Orange red
		}
		else if (temperature<5200)
		{
			// K class
			return 0xffd2a1;	// Yellow orange
		}
		else if (temperature<6000)
		{
			// G class
			return 0xfff4ea;	// Yellowish white
		}
		else if (temperature<7500)
		{
			// F class
			return 0xf8f7ff;	// White
		}
		else if (temperature<10000)
		{
			// A class
			return 0xcad7ff;	// White to blue white
		}
		else if (temperature<30000)
		{
			// B class
			return 0xaabfff;	// Blue white
		}
		else
		{
			// O class
			return 0x9bb0ff;	// Blue
		}
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
		if (this.getState()!=DBObject.stateTypes.NEW)
			this.setState(DBObject.stateTypes.MODIFIED);
	}

	/**
	 * Star generation routine
	 */
	public void create() {
		// Create planets
		int planetsCount = Galaxy.getInstance().generator.nextInt(10);
		Planet currentPlanet;
		for (int i=0;i<planetsCount;i++)
		{
			currentPlanet = new Planet();
			
			currentPlanet.create();
			currentPlanet.setId(i);
			
			this.planets.add(currentPlanet);
		}
	}
	
	public String toString()
	{
		return "Star #"+getId()
			+", "+getLocation().toString()
			+", "+getName()
			+", "+getTemperature()+"K";
	}

	public Coordinate getLocation() {
		return this.location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
		if (this.getState()!=DBObject.stateTypes.NEW)
			this.setState(DBObject.stateTypes.MODIFIED);
	}

	public PlanetArray getPlanets() {
		return planets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (this.getState()!=DBObject.stateTypes.NEW)
			this.setState(DBObject.stateTypes.MODIFIED);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) 
	{
		if (this.id != -1)
		{
			// TODO exception
			return;
		}
		
		this.id = id;
		
		if (this.getState()!=DBObject.stateTypes.NEW)
			this.setState(DBObject.stateTypes.MODIFIED);
	}
	
	/**
	 * draw star into given graphics object
	 * 
	 * @param g2 graphic object draw to
	 * @param scale scale level
	 * @param x position of star
	 * @param y position of star
	 * @param showName true if name of star should be displayed near
	 * @param showId true if id of star should be displayed near
	 * @param isHover true if display mouse over event
	 */
	public void draw(Graphics2D g2, double scale, int x, int y, boolean showName, boolean showId, boolean isHover)
	{
		int diameter = (int)scale + 5;
		if (diameter < 1) {
			diameter = 1;
		}
		
		int star_x = (int)(x + scale * getLocation().getX() - diameter/2);
		int star_y = (int)(y + scale * getLocation().getY() - diameter/2);

		g2.setColor(new Color(getTemperatureAsRGB()));

		g2.fillOval(star_x, star_y, diameter, diameter);

		if (isHover) {
			g2.setColor(Color.white);
		} else {
			g2.setColor(Color.gray);
		}
		
		int textOffset = 0;
		if (showId)
		{
			textOffset+=12;
			g2.drawString("#" + getId(), star_x + diameter,
					star_y + diameter + textOffset);
		}
		
		if (showName || isHover)
		{
			textOffset+=12;
			g2.drawString(getName(), star_x + diameter, 
					star_y + diameter + textOffset);
		}

		if (isHover) {
			g2.setColor(Color.white);
			g2.drawOval(star_x - 2, star_y - 2, diameter + 4,
					diameter + 4);
		}
	}
}
