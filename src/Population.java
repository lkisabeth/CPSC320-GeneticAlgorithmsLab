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
        Chromosome mostFit = null;
        // track the highest Fitness (so .getFitness doesn't need called repeatedly. initially 0)
        double bestFitness = 0;

        // check the fitness of each individual Chromosome in the Population
        for (Chromosome c: chromosomes) {
            // get Fitness
            double fitness = c.getFitness();
            System.out.println(fitness);
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

    public void breed() {
        // track the lowest Fitness (so .getFitness doesn't need called repeatedly. initially 1)
        double lowestFitness = 1;

        // check the fitness of each individual Chromosome in the Population
        for (Chromosome c: chromosomes) {
            // get Fitness
            double fitness = c.getFitness();
            // check if this fitness is lower than previous lowest
            if (fitness < lowestFitness && fitness != 0) {
                // if it is, update the lowestFitness
                lowestFitness = fitness;
            }
        }

        // create a new population
        Population newPop = new Population();
        // set the mutation rate
        newPop.mutationRate = mutationRate;

        // create a lottery ArrayList
        ArrayList<Chromosome> lottery = new ArrayList<>();
        // for each chromosome in the population
        for (Chromosome c: chromosomes) {
            // get it's fitness
            double fitness = c.getFitness();
            // calculate the number of tickets
            int tickets = (int)Math.round(fitness/lowestFitness);
            // add it to the lottery once for each ticket
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
