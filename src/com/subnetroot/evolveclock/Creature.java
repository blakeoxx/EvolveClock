package com.subnetroot.evolveclock;

import org.apache.commons.lang3.ArrayUtils;

public class Creature
{
	private Limb[] limbs;
	private RotationPlan rotPlan;
	private int fitness;
	private boolean fitnessCalculated;
	
	private final LimbAnchor[] boardAnchors = {
			new LimbAnchor(), new LimbAnchor(),
			new LimbAnchor(), new LimbAnchor(),
			new LimbAnchor(), new LimbAnchor()};
	
	public Creature(LimbDNA limbDNA, RotationPlan rotPlan)
	{
		limbs = new Limb[limbDNA.limbCount()];
		for (int i = 0; i < limbDNA.limbCount(); i++)
		{
			if (limbDNA.getLimbParent(i) < LimbDNA.LIMB_MAX) limbs[i] = new Limb(limbs[limbDNA.getLimbParent(i)], limbDNA.getLimbOrientation(i));
			else limbs[i] = new Limb(boardAnchors[limbDNA.getLimbParent(i)-LimbDNA.ATTACHMENT_BOARD_MIN], limbDNA.getLimbOrientation(i));
		}
		
		this.rotPlan = new RotationPlan(rotPlan);
		fitness = 0;
		fitnessCalculated = false;
	}
	
	public LimbDNA getLimbDNA() throws Exception
	{
		LimbDNA limbDNA = new LimbDNA();
		
		// LimbDNA doesn't accept parent limbs that come after child limbs. Reorder limbs
		Limb[] limbsSorted = limbs;
		for (int i = 0; i < limbsSorted.length; i++)
		{
			int limbIdx = ArrayUtils.indexOf(limbsSorted, limbsSorted[i].getParent());
			if (limbsSorted[i].getParent().getClass() == Limb.class && limbIdx > i) ArrayUtils.swap(limbsSorted, i, limbIdx);
		}
		
		// Add the limbs to the LimbDNA
		for (int i = 0; i < limbs.length; i++)
		{
			if (limbs[i].getParent().getClass() == LimbAnchor.class)
			{
				// This limb's parent/attachment is a board anchor
				int anchorIdx = ArrayUtils.indexOf(boardAnchors, limbs[i].getParent());
				if (anchorIdx == ArrayUtils.INDEX_NOT_FOUND || limbDNA.addLimb((byte)(anchorIdx+LimbDNA.ATTACHMENT_BOARD_MIN), limbs[i].getWorldOrientation()) == false) throw new Exception("Could not create DNA for limb "+i+" of Creature. Parent board anchor not found");
			}
			else
			{
				// This limb's parent/attachment is another limb
				int limbIdx = ArrayUtils.indexOf(limbs, limbs[i].getParent());
				if (limbIdx == ArrayUtils.INDEX_NOT_FOUND || limbDNA.addLimb((byte)limbIdx, limbs[i].getWorldOrientation()) == false) throw new Exception("Could not create DNA for limb "+i+" of Creature. Parent limb not found");
			}
		}
		
		return limbDNA;
	}
	
	public RotationPlan getRotationPlan()
	{
		return rotPlan;
	}
	
	public int getFitness()
	{
		if (!fitnessCalculated)
		{
			fitness = 0;
			// TODO: Calculate fitness
			fitnessCalculated = true;
		}
		return fitness;
	}
}