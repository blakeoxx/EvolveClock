package com.subnetroot.evolveclock;

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
		
		rotPlan = new RotationPlan(rotPlan);
		fitness = 0;
		fitnessCalculated = false;
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