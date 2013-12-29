package world;

import java.util.*;
import util.*;
import world.generator.GalaxyGenerator;
import world.generator.GalaxyGeneratorChaotic;

public class Galaxy {
	// Random generator with predefined seed to use in generate()
	protected Random generator;

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
        if (this.stars==null) {
            this.stars= new StarArray();
        } else {
            this.stars.clear();
        }

        GalaxyGenerator generator = new GalaxyGeneratorChaotic(minDistance, maxDistance);
        this.stars = generator.generate();
    }

	public static StarArray getStars() {
		return getInstance().stars;
	}

    public static Random getGenerator() {
        return getInstance().generator;
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
