package util;

import java.awt.Rectangle;
import java.util.ArrayList;

import world.Star;

public class StarArray extends ArrayList<Star> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8039273398434330805L;
	
	
	StarArrayDBsqlite db;
	public StarArray() 
	{
		super();
		
		try {
			this.db = new StarArrayDBsqlite(this);
		} catch (Exception e) {
			Trace.critical("Can't connect to database: "+e.getMessage());
		}	
	}

	@Override
	public void clear()
	{
		super.clear();
		try {
			db.clear();
		} catch (Exception e) {
			Trace.critical("Galaxy cleanup failed: "+e.getMessage());
		}	
	}

	/**
	 * Find nearest star to given coordinate
	 * 
	 * @param Coordinate coord
	 * @return int
	 */
	public int getNearest(Coordinate coord)
	{
		int nearest = -1;
		double nearestDistance = 1E20;
		
		for (int i=0;i<this.size();i++)
		{
			double currDistance = get(i).getDistance(coord);
			
			if (currDistance < nearestDistance)
			{
				nearest = i;
				nearestDistance = currDistance;
			}
		}
		
		return nearest;
	}

	/**
	 * Find distance from given coordinate to nearest star  
	 * 
	 * @param Coordinate coord
	 * @return double
	 */
	public double getNearestDistance(Coordinate coord)
	{
		return get(getNearest(coord)).getDistance(coord);
	}
	
	/**
	 * Find and return star by name
	 * 
	 * @param String name
	 * @return Star
	 */
	public Star getByName(String name)
	{
		for (int i=0;i<this.size();i++)
		{
			if (this.get(i).getName().equals(name))
				return this.get(i);
		}
		
		return null;
	}
	
	public Rectangle getBoundsRectangle()
	{
		Rectangle boundsRect = new Rectangle();
		
		for (int i=0;i<this.size();i++)
		{
			Coordinate coord = this.get(i).getLocation();
			int x = coord.getX();
			int y = coord.getY();
			
			if (x<boundsRect.x)
				boundsRect.x = x;
			if (x>boundsRect.x+boundsRect.width)
				boundsRect.width = x-boundsRect.x;
			if (y<boundsRect.y)
				boundsRect.y = y;
			if (y>boundsRect.y+boundsRect.height)
				boundsRect.height = y-boundsRect.y;
		}

		return boundsRect;
	}
	
	public void save() throws Exception
	{
		db.save();
	}
	
	public void load() throws Exception
	{
		super.clear();
		db.load();
	}
}
