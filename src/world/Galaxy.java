package world;

import java.util.*;
import util.*;

public class Galaxy {
	// Random generator with predefined seed to use in generate()
	public Random generator;

	public final static int maxDistance = 150;
	public final static int minDistance = 20;

	protected StarArray stars;

	// Singleton realization
	private static final Galaxy INSTANCE = new Galaxy();

	private Galaxy() {
		setSeed(System.currentTimeMillis());

		load();
	}

	public static Galaxy getInstance() {
		return INSTANCE;
	}

	// Random generator initialization
	public void setSeed(long seed) {
		this.generator = new Random(seed);
	}

	// Generating galaxy
	public void create() {
		Trace.message("1. God #" + Math.abs(this.generator.nextInt())
				+ " made heaven & earth ");

		int count = generator.nextInt(2000)+200;

		if (this.stars==null)
		{
			this.stars= new StarArray();
		}
		else 
		{
			this.stars.clear();
		}
		
		for (int i = 0; i < count; i++) {
			Star currentStar = new Star();

			// Generate star location: not so far (maxDistance) from previous
			// and not so close (minDistance) to any another
			if (i == 0) {
				currentStar.getLocation().set(0, 0);
			} else {
				// Star position
				Star previousStar = this.stars.get(i - 1);

				Coordinate location = new Coordinate();
				int iteration = 0;
				int currentMaxDistance = maxDistance;
				do {
					location.setX(previousStar.getLocation().getX()
							+ this.generator.nextInt(currentMaxDistance * 2)
							- currentMaxDistance);
					location.setY(previousStar.getLocation().getY()
							+ this.generator.nextInt(currentMaxDistance * 2)
							- currentMaxDistance);
					if (iteration++ > 10) {
						currentMaxDistance *= 2;
					}
				} while (this.stars.getNearestDistance(location) < minDistance);
				currentStar.getLocation().set(location);

			}

			// Star temperature
			// Star temperature should be in 2000-40000K
			// http://en.wikipedia.org/wiki/Stellar_classification
			//
			int temperature = (int)(Math.abs(this.generator.nextGaussian()*7000) + 2000);
			if (temperature>40000)
			{
				temperature = 40000;
			}
			else if (temperature < 2200)
			{
				// Special case for classes below M
				temperature = this.generator.nextInt(2000);
			}
			currentStar.setTemperature(temperature);

			// Generate and set unique star name
			String currentStarName;
			do {
				currentStarName = StarName.getName();
			} while (this.stars.getByName(currentStarName) != null);
			currentStar.setName(currentStarName);

			currentStar.setId(i);

			currentStar.create();
			
			Trace.message(currentStar.toString());

			this.stars.add(i, currentStar);
		}

		Trace.message("2. " + this.stars.size() + " stars are shining brightly");


		Trace.message("3. Universe is awaiting you");
	}

	public static StarArray getStars() {
		return getInstance().stars;
	}
	
	public void save()
	{
		try {
			this.stars.save();
			Trace.message("Galaxy saved successfully");
		} catch (Exception e) {
			Trace.critical("Galaxy saving failed: "+e.getMessage());
		}		
	}

	public void load()
	{
		this.stars = new StarArray();
		try {
			this.stars.load();
			Trace.message("Galaxy: "+this.stars.size()+" stars loaded");
		} catch (Exception e) {
			Trace.critical("Galaxy loading failed: "+e.getMessage());
		}
	}
}
