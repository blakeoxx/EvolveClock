package com.subnetroot.evolveclock;

import org.apache.commons.lang3.ArrayUtils;

public class RotationPlan
{
	// The multi-dimensional rotations array represents the direction and number of 90 degree turns per digit step.
	// rotations[s] = each digit step s
	// rotations[s][t] = limb t's turns for the digit step s (positive numbers represent clockwise turns)
	private byte[][] rotations;
	
	// Constants for turnValue (argument to setLimbRotation, etc.)
	public static final byte TURN_NONE = 0;
	public static final byte TURN_CLOCKWISE = 1;
	public static final byte TURN_COUNTER = -1;
	public static final byte TURN_MIRROR = 2;
	
	public RotationPlan(int stepCount, int limbCount)
	{
		this.rotations = new byte[stepCount][limbCount];
	}
	
	public RotationPlan(byte[][] rotations)
	{
		this.rotations = rotations;
	}
	
	public RotationPlan(RotationPlan rotationPlan)
	{
		this.rotations = rotationPlan.getAllStepRotations();
	}
	
	public byte[][] getAllStepRotations()
	{
		return rotations;
	}
	
	public byte[] getStepRotations(int step) throws Exception
	{
		if (step < 0 || step >= rotations.length) throw new Exception("Step "+step+" is out of range. Rotation plan contains "+rotations.length+" steps");
		return rotations[step];
	}
	
	public byte getLimbRotation(int step, int limb) throws Exception
	{
		byte[] stepRots = getStepRotations(step);
		if (limb < 0 || limb >= stepRots.length) throw new Exception("Limb "+limb+" is out of range. Rotation plan contains "+stepRots.length+" limbs");
		return stepRots[limb];
	}
	
	public void setLimbRotation(int step, int limb, byte turnValue) throws Exception
	{
		if (turnValue != RotationPlan.TURN_NONE && turnValue != RotationPlan.TURN_CLOCKWISE
			&& turnValue != RotationPlan.TURN_COUNTER && turnValue != RotationPlan.TURN_MIRROR) throw new Exception("Invalid rotation value");
		else if (step < 0 || step >= rotations.length) throw new Exception("Step "+step+" is out of range. Rotation plan contains "+rotations.length+" steps");
		else if (limb < 0 || limb >= rotations[step].length) throw new Exception("Limb "+limb+" is out of range. Rotation plan contains "+rotations[step].length+" limbs");
		rotations[step][limb] = turnValue;
	}
	
	public void addLimb(int atPosition) throws Exception
	{
		if (atPosition < 0) throw new Exception("Limb position must be zero or positive");
		atPosition = Math.min(atPosition, rotations[0].length);
		for (int s = 0; s < rotations.length; s++)
		{
			rotations[s] = ArrayUtils.insert(atPosition, rotations[s], RotationPlan.TURN_NONE);
		}
	}
	
	public void removeLimb(int atPosition)
	{
		if (atPosition < 0 || atPosition >= rotations[0].length) return;
		for (int s = 0; s < rotations.length; s++)
		{
			rotations[s] = ArrayUtils.remove(rotations[s], atPosition);
		}
	}
}