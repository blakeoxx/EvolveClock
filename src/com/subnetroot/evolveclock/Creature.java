package com.subnetroot.evolveclock;

public class Creature
{
	private Limb[] limbs;
	private RotationPlan rotPlan;
	private int fitness;
	private boolean fitnessCalculated;
	
	public Creature(LimbGene limbGene, RotationPlanGene rotGene)
	{
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