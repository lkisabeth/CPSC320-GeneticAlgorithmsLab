public class main {

    public static void main(String[] args) {
        //region Config
        int popSize = 100; // number of Chromosomes in the population (100 is recommended starting point, but tweak as needed)
        int numGenerations = 100000; // number of iterations the program will run (100k is recommended starting point, but tweak as needed)
        double fitnessGoal = .99999; // desired fitness level (.99999 used as default for BinaryChromosome implementation)
        //endregion

        //region Instantiation
        // Create a new population
        Population pop = new Population();

        // Add popSize Chromosomes to the population
        for (int i = 1; i < popSize; i++) {
            pop.addChromosome(new BinaryChromosome(8)); // Use provided BinaryChromosome implementation
        }
        //endregion

        //region Breeding Loop
        // Run to the breeding program numGenerations times
        for (int i = 0; i < numGenerations; i++) {
            // use .evaluate() to retrieve the Chromosome with the highest fitness
            Chromosome best = pop.evaluate();
            // store the current fitness so .getFitness() only needs to be called once.
            double currentBestFitness = best.getFitness();
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
