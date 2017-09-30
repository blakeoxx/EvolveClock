package com.subnetroot.evolveclock;

public class Limb extends LimbAnchor
{
	private LimbAnchor parent;
	private byte worldOrientation;	// The direction this limb is pointing in the world (drawn from origin to end)
	private byte relativeOrientation;	// The direction this limb is pointing as an extension of the parent (drawn from origin to end)
	
	// Constants for orientation
	public static final byte ORIENTATION_NORTH = 0;
	public static final byte ORIENTATION_FORWARD = 0;
	public static final byte ORIENTATION_EAST = 1;
	public static final byte ORIENTATION_RIGHT = 1;
	public static final byte ORIENTATION_SOUTH = 2;
	public static final byte ORIENTATION_BACK = 2;
	public static final byte ORIENTATION_WEST = 3;
	public static final byte ORIENTATION_LEFT = 3;
	
	public Limb(LimbAnchor parent, byte worldOrientation)
	{
		super();
		this.parent = parent;
		setWorldOrientation(worldOrientation);
	}
	
	public byte getWorldOrientation()
	{
		return worldOrientation;
	}
	
	public void setWorldOrientation(byte newOrientation)
	{
		newOrientation = normalizeOrientation(newOrientation);
		if (newOrientation != worldOrientation)
		{
			worldOrientation = newOrientation;
			updateRelativeOrientation();
			updateChildenOrientations();
		}
	}
	
	// Set the world orientation in regards to the relative and parent orientations
	private void updateWorldOrientation()
	{
		worldOrientation = normalizeOrientation(parent.getWorldOrientation() + getRelativeOrientation());
	}
	
	public byte getRelativeOrientation()
	{
		return relativeOrientation;
	}
	
	public void setRelativeOrientation(byte newOrientation)
	{
		newOrientation = normalizeOrientation(newOrientation);
		if (newOrientation != relativeOrientation)
		{
			relativeOrientation = newOrientation;
			updateWorldOrientation();
			updateChildenOrientations();
		}
	}
	
	// Set the relative orientation in regards to the world and parent orientations
	private void updateRelativeOrientation()
	{
		relativeOrientation = normalizeOrientation(getWorldOrientation() - parent.getWorldOrientation());
	}
	
	// Normalize a given number to be used as a valid orientation (a positive number between 0 and 3 inclusive)
	private byte normalizeOrientation(byte orientation)
	{
		orientation = (byte)(orientation % 4);
		if (orientation < 0) orientation += 4;
		return orientation;
	}
	
	// Event listener for parent orientation changes
	public void parentRotated()
	{
		updateWorldOrientation();
		updateChildenOrientations();
	}
}