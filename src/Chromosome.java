interface Chromosome {
    Chromosome crossover(Chromosome other);
    void mutate(double mutationRate);
    double getFitness();
    int getNumber (int first, int last);
}
