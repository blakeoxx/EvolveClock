package com.subnetroot.evolveclock;

import java.util.ArrayList;
import java.util.Arrays;

public class LimbGene
{
	private ArrayList<Byte> limbList;		// An array list of bytes representing each limb of a creature. bits[2-6] = attachment (index of parent in array list, or board anchor reference if greater than 15), bits[7-8] = initial world orientation
	
	private static final byte LIMB_MAX = (byte)(Math.pow(2,4)-1);
	private static final byte ATTACHMENT_MAX = (1 << 4) + 6;
	private static final byte ATTACHMENT_BOARD_MIN = (1 << 4) + 1;
	private static final byte ATTACHMENT_BOARD_MAX = (1 << 4) + 6;
	
	public LimbGene()
	{
		limbList = new ArrayList<Byte>(0);
	}
	
	public LimbGene(String genecode) throws Exception
	{
		String[] genes = genecode.split(" ");
		if (genes.length > LimbGene.LIMB_MAX) throw new Exception("Could not create LimbGene. Code contains too many limbs");
		
		limbList = new ArrayList<Byte>(genes.length);
		for (int i=0; i<genes.length; i++)
		{
			byte thisGene = (new Byte(genes[i])).byteValue();
			byte attachment = (byte)(thisGene >> 2);
			byte worldOrientation = (byte)(thisGene & 0b11);
			if (attachment < 0 || attachment > LimbGene.ATTACHMENT_MAX || worldOrientation < 0 || worldOrientation > 3) throw new Exception("Could not create LimbGene. Gene "+i+" attachment or orientation out of bounds");
			else if ((attachment <= LimbGene.LIMB_MAX && attachment >= genes.length) || (attachment > LimbGene.LIMB_MAX && attachment < LimbGene.ATTACHMENT_BOARD_MIN)) throw new Exception("Could not create LimbGene. Gene "+i+" attachment or orientation out of bounds");
			limbList.add(thisGene);
		}
	}
	
	public String getGeneCode()
	{
		String genecode = "";
		for (int i=0; i<limbList.size(); i++)
		{
			genecode += limbList.get(i) + " ";
		}
		return genecode.trim();
	}
	
	public int limbCount()
	{
		return limbList.size();
	}
	
	public boolean addLimb(byte attachment, byte worldOrientation)
	{
		if (limbList.size() >= LimbGene.LIMB_MAX) return false;
		else if (attachment < 0 || attachment > LimbGene.ATTACHMENT_MAX || worldOrientation < 0 || worldOrientation > 3) return false;
		else if ((attachment <= LimbGene.LIMB_MAX && attachment >= limbList.size()) || (attachment > LimbGene.LIMB_MAX && attachment < LimbGene.ATTACHMENT_BOARD_MIN)) return false;
		
		return limbList.add((byte)((attachment << 2) + worldOrientation));
	}
	
	public boolean removeLimb(int index)
	{
		if (index >= limbList.size()) return false;
		else if (getLimbChildren(index).length > 0) return false;
		
		limbList.remove(index);
		
		// Update parent indexes
		for (int i=0; i<limbList.size(); i++)
		{
			byte thisAttachment = (byte)(limbList.get(i) >> 2);
			if (thisAttachment <= LimbGene.LIMB_MAX && thisAttachment > index) setLimbParent(i, (byte)(thisAttachment-1));
		}
		
		return true;
	}
	
	public int[] getLimbChildren(int index)
	{
		int[] children = new int[limbList.size()];
		
		int childCount = 0;
		for (int i=0; i<limbList.size(); i++)
		{
			byte thisLimb = limbList.get(i);
			if (thisLimb >> 2 == index) children[childCount++] = i;
		}
		
		return Arrays.copyOf(children,childCount);
	}
	
	public byte getLimbParent(int index)
	{
		return (byte)(limbList.get(index) >> 2);
	}
	
	public boolean setLimbParent(int index, byte attachment)
	{
		if (index >= limbList.size()) return false;
		else if (attachment < 0 || attachment > LimbGene.ATTACHMENT_MAX) return false;
		else if ((attachment <= LimbGene.LIMB_MAX && attachment >= limbList.size()) || (attachment > LimbGene.LIMB_MAX && attachment < LimbGene.ATTACHMENT_BOARD_MIN)) return false;
		
		byte thisLimb = limbList.get(index);
		limbList.set(index, (byte)((attachment << 2) + (thisLimb & 0b11)));
		return true;
	}
	
	public byte getLimbOrientation(int index)
	{
		return (byte)(limbList.get(index) & 0b11);
	}
	
	public boolean setLimbOrientation(int index, byte worldOrientation)
	{
		if (index >= limbList.size()) return false;
		else if (worldOrientation < 0 || worldOrientation > 3) return false;
		
		byte thisLimb = limbList.get(index);
		limbList.set(index, (byte)(((thisLimb >> 2) << 2) + worldOrientation));
		return true;
	}
}