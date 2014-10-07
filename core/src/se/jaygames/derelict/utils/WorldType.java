package se.jaygames.derelict.utils;

/**
 * Used for worlds. Each world type have 
 * a name, and a path to the image from a pack file 
 * @author Johan Fredin
 *
 */
public enum WorldType {
	
	/** The grass world */
	START("test");
	
	private String name;
	
	/**
	 * Create a new WorldType instance
	 * @param displayName the display name of the world
	 */
	private WorldType(String displayName) {
		this.name = displayName;
	}
	
	/**
	 * @return the name of the world to be displayed
	 */
	public String getDisplayName() {
		return name;
	}
	
	
}
