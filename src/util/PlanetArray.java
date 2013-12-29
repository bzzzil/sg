package util;

import java.util.ArrayList;
import world.*;

public class PlanetArray extends ArrayList<Planet> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3860251336614627035L;

	private final Star star;
	
	public PlanetArray(Star star) 
	{
		super();
		
		this.star = star;
	}

	public void save() throws Exception
	{
	}
	
	public void load() throws Exception
	{
	}
}
