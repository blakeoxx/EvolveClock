package com.subnetroot.evolveclock;

import java.util.Arrays;
import java.util.List;

public class EvolveClock
{
	private static short POPULATION_SIZE = 100;			// How many creatures each generation should contain
	private static short MAX_GENERATION = 1000;			// How many generations to creature
	private static short BEST_CREATURES_COUNT = 100;	// How many creatures to save in the high scores
	
	private short generation;			// Generation counter
	private Creature[] bestCreatures;	// Highest scoring creatures
	
	public EvolveClock()
	{
		generation = 0;
		bestCreatures = new Creature[BEST_CREATURES_COUNT];
		
		while (generation < MAX_GENERATION)
		{
			Creature[] population = createPopulation(POPULATION_SIZE);
			saveBestCreatures(population);
		}
		
		showBestCreatures();
	}
	
	public static void main(String[] args)
	{
		new EvolveClock();
	}
	
	private Creature[] createPopulation(short popSize)
	{
		Creature[] newPop = new Creature[popSize];
		for (int i = 0; i < popSize; i++)
		{
			// TODO generate a creature and add it to newPop[i]
		}
		return newPop;
	}
	
	private void saveBestCreatures(Creature[] population)
	{
		List<Creature> bestCreatureList = Arrays.asList(bestCreatures);
		
		// Loop through the population
		for (int popIdx = 0; popIdx < population.length; popIdx++)
		{
			Creature thisCreature = population[popIdx];
			if (bestCreatureList.contains(thisCreature)) continue;		// Best creature list already contains this creature
			
			// Insert this creature into the best creature list in order, if it has a better score than others in the list
			for (int bestIdx = 0; bestIdx < bestCreatureList.size(); bestIdx++)
			{
				Creature curBestCreature = bestCreatureList.get(bestIdx);
				if (curBestCreature == null || curBestCreature.getFitness() < thisCreature.getFitness())
				{
					bestCreatureList.add(bestIdx, thisCreature);
					bestCreatureList.remove(bestCreatureList.size());
					break;
				}
			}
		}
		bestCreatures = bestCreatureList.toArray(bestCreatures);
	}
	
	// Output the fitness and genome for the highest scoring creatures
	private void showBestCreatures()
	{
		System.out.println("#\tFitness\tGenome");
		for (int i = 0; i < bestCreatures.length; i++)
		{
			if (bestCreatures[i] == null)
			{
				System.out.println(i+1 + "\tUNDEFINED");
				continue;
			}
			
			String genome;
			try
			{
				genome = bestCreatures[i].getLimbDNA().getGeneCode();
			}
			catch (Exception e)
			{
				genome = "ERROR: " + e;
			}
			System.out.println(i+1 + "\t" + bestCreatures[i].getFitness() + "\t" + genome);
		}
		System.out.println("---\t---\t---");
	}
}