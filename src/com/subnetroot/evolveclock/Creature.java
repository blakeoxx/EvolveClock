package com.subnetroot.evolveclock;

public class Creature
{
	private Limb[] limbs;
	private RotationPlan rotPlan;
	private int fitness;
	
	public Creature(LimbGene limbGene, RotationPlanGene rotGene)
	{
		fitness = null;
	}
	
	public int getFitness()
	{
		if (fitness == null)
		{
			fitness = 0;
			// TODO: Calculate fitness
		}
		return fitness;
	}
}