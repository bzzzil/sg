package util;

import java.util.ArrayList;
import world.*;

public class PlanetArray extends ArrayList<Planet> {
	Star star;
	
	public PlanetArray(Star star) 
	{
		super();
		
		this.star = star;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860251336614627035L;

	public void save() throws Exception
	{
	}
	
	public void load() throws Exception
	{
	}
}
