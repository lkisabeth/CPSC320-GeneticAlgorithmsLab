import java.util.ArrayList;
import java.util.Random;

public class Population {
    ArrayList<Chromosome> chromosomes = new ArrayList<>();
    private double mutationRate = 0;

    public Population() {}

    public void addChromosome(Chromosome c) {
        // add the given Chromosome to the chromosomes ArrayList
        chromosomes.add(c);
    }

    public Chromosome evaluate() {
        // store most fit individual
        Chromosome mostFit = chromosomes.get(0);
        // track the highest Fitness (so .getFitness doesn't need called repeatedly. initially 0)
        double bestFitness = 0;

        // check the fitness of each individual Chromosome in the Population
        for (Chromosome c: chromosomes) {
            // get Fitness
            double fitness = c.getFitness();
            // check if this fitness is higher than previous best
            if (fitness > bestFitness) {
                // if it is, update the highestFitness
                bestFitness = fitness;
                // and store this individual
                mostFit = c;
            }
        }
        // return the most fit individual
        return mostFit;
    }

    /*
    The breed method on the population is called to create a new population by breeding the most fit individuals.
    Use a lottery system for selecting which individuals are most likely to be chosen for reproduction.
    See below for details. Note that if a child chromosome is created that has a fitness of 0, it should be discarded.

    The lottery system allows any chromosome to be selected to reproduce for the next generation, but weights the selection toward more fit individuals.
    Start by identifying the chromosome that is least fit (but with a fitness greater than 0).
    Let's say that the least fit chromosome has a fitness of 0.01.
    All chromosomes get a number of tickets based on comparing its fitness to the least fit chromosome's fitness.
    So if a chromosome had a fitness of .25, and the least fit chromosome had a fitness of .01, we'd divide .25 by .01 to get 25 tickets.
    The least fit chromosome will, by definition, get only 1 ticket. Allow natural rounding on the result (so a result of 1.25 tickets will get 1 ticket).
    At the end, generate two random numbers from 1 to the number of tickets available, and use those two chromosomes to generate a chromosome for the new population.
    Do that over and over until the population is back to its initial size.
    It's okay for the same chromosome to be picked as both parents. The net effect is that the chromosome passed into the next generation unchanged (except possibly for mutation).
    */
    public void breed() {
        // store the least fit individual
        Chromosome leastFit = chromosomes.get(0);
        // track the lowest Fitness (so .getFitness doesn't need called repeatedly. initially 0)
        double lowestFitness = 1;

        // check the fitness of each individual Chromosome in the Population
        for (Chromosome c: chromosomes) {
            // get Fitness
            double fitness = c.getFitness();
            // check if this fitness is lower than previous lowest
            if (fitness < lowestFitness) {
                // if it is, update the lowestFitness
                lowestFitness = fitness;
                // and store this individual
                leastFit = c;
            }
        }

        // create a new population
        Population newPop = new Population();
        // set the mutation rate
        newPop.mutationRate = mutationRate;

        // create a lottery system
        ArrayList<Chromosome> lottery = new ArrayList<>();
        // for each chromosome in the population
        for (Chromosome c: chromosomes) {
            // get the fitness
            double fitness = c.getFitness();
            // calculate the number of tickets
            int tickets = (int)Math.round(fitness/lowestFitness);
            // add the chromosome to the lottery the number of times equal to the number of tickets
            for (int i = 0; i < tickets; i++) {
                lottery.add(c);
            }
        }

        // create a random number generator
        Random random = new Random();
        // while the new population is smaller than the original population
        while (newPop.chromosomes.size() < chromosomes.size()) {
            // generate two random numbers from 1 to the number of tickets available
            int first = random.nextInt(lottery.size());
            int second = random.nextInt(lottery.size());
            // use those two chromosomes to generate a chromosome for the new population
            Chromosome child = lottery.get(first).crossover(lottery.get(second));
            // mutate the child
            child.mutate(mutationRate);
            // if the child's fitness is > 0, add it to the new population
            if (child.getFitness() > 0) {
                newPop.addChromosome(child);
            }
        }

        // replace the old population with the new population
        chromosomes = newPop.chromosomes;
    }

    /*
    The setMutationRate is called to set the mutation rate...this should be checked during breeding after an offspring has been created, to see if that offspring needs mutated.
     */
    public void setMutationRate(double rate) {
        mutationRate = rate;
    }
}
