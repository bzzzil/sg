package util;

public class DBObject {
	public enum stateTypes {UNCHANGED, NEW, MODIFIED, REMOVED};
	stateTypes state;
	
	public DBObject()
	{
    	this.setState(stateTypes.NEW);
	}
	
	public stateTypes getState() 
	{
		return this.state;
	}
	
	public void setState(stateTypes state) 
	{
		this.state = state;
	}
}
