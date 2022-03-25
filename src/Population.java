import java.util.ArrayList;

public class Population {
    ArrayList<Chromosome> chromosomes = new ArrayList<>();

    public Population() {}

    public void addChromosome(Chromosome c) {
        // add the given Chromosome to the population array
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

    }

    public void setMutationRate(double rate) {

    }
}
