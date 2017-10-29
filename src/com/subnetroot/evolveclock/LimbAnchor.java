package com.subnetroot.evolveclock;

import java.util.ArrayList;
import java.util.Iterator;

public class LimbAnchor
{
	private ArrayList<Limb> attachedChildren;
	
	public LimbAnchor()
	{
		attachedChildren = new ArrayList<Limb>(0);
	}
	
	public Limb[] getAttachedChildren()
	{
		return (Limb[])attachedChildren.toArray();
	}
	
	public void addAttachedChild(Limb limb)
	{
		attachedChildren.add(limb);
	}
	
	public void updateChildenOrientations()
	{
		Iterator<Limb> itr = attachedChildren.iterator();
		while (itr.hasNext())
		{
			itr.next().parentRotated();
		}
	}
	
	public byte getWorldOrientation()
	{
		return Limb.ORIENTATION_NORTH;
	}
	
	public LimbAnchor getParent()
	{
		return null;
	}
}