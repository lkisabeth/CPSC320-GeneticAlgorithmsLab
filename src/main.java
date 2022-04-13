public class main {
    public static void main(String[] args) {
        //region Config
        int popSize = 100; // number of Chromosomes in the population (100 is recommended starting point, but tweak as needed)
        int numGenerations = 100000; // number of iterations the program will run (100k is recommended starting point, but tweak as needed)
        double fitnessGoal = .75; // desired fitness level (.99999 used as default for BinaryChromosome implementation)
        double mutationRate = 0.5;
        //endregion

        //region Instantiation
        // Create a new population
        Population pop = new Population();
        // Set the mutationRate
        pop.setMutationRate(mutationRate);

        // Add popSize Chromosomes to the population
        for (int i = 1; i < popSize; i++) {
            BinaryChromosome c = new BinaryChromosome(8);
            for (int j = 0; j < c.getBits().length; j++) {
                System.out.print(c.getBits()[j]);
            }
            System.out.println();
            pop.addChromosome(c); // Use provided BinaryChromosome implementation
        }
        //endregion

        //region Breeding Loop
        double currentBestFitness = 0;
        // Run to the breeding program numGenerations times
        for (int i = 0; i < numGenerations; i++) {
            // use .evaluate() to retrieve the Chromosome with the highest fitness
            Chromosome best = pop.evaluate();
            double bestFitness = best.getFitness();
            // store the current fitness so .getFitness() only needs to be called once.
            if (bestFitness > currentBestFitness) currentBestFitness = bestFitness;
            // print the current best fitness out to the console
            System.out.println("Current Best: " + currentBestFitness);

            // if the fitness of the individual meets or exceeds the desired fitness, stop breeding
            if (currentBestFitness >= fitnessGoal) break;

            // use .breed() to create a new population (generation) using the most fit individuals
            pop.breed();
        }
        //endregion
    }
}
