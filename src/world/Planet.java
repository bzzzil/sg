package world;

import util.*;

public class Planet extends DBObject {
	private int id = -1;
	private int distance;
	
	/**
	 * Galaxy generation routine
	 * 
	 */
	public void create() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (this.id != -1)
		{
			// TODO exception
			return;
		}
		
		this.id = id;
		
		if (this.getState()!=DBObject.stateTypes.NEW)
			this.setState(DBObject.stateTypes.MODIFIED);
	}

	public void setDistance(int distance) {
		this.distance = distance;
    	this.setState(DBObject.stateTypes.MODIFIED);
	}

	public int getDistance() {
		return distance;
	}
}
