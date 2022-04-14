import java.util.Random;

public class LineEquationChromosome implements Chromosome {
    protected double A;
    protected double B;
    protected double C;
    protected double x;
    protected double y;
    private final Random random = new Random();

    public LineEquationChromosome(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void randomize() {
        // define upper/lower bound for the values of A, B, and C.
        double max = 30;
        // randomize the values of A, B, and C while allowing for fractions and negatives
        A = random.nextDouble(max) * (random.nextBoolean() ? -1 : 1);
        B = random.nextDouble(max) * (random.nextBoolean() ? -1 : 1);
        C = random.nextDouble(max) * (random.nextBoolean() ? -1 : 1);
    }

    public double getA() { return A; }
    public double getB() { return B; }
    public double getC() { return C; }

    @Override
    public void mutate(double mutationRate) {
        if (random.nextDouble() <= mutationRate) A = random.nextDouble();
        if (random.nextDouble() <= mutationRate) B = random.nextDouble();
        if (random.nextDouble() <= mutationRate) C = random.nextDouble();
    }

    @Override
    public Chromosome crossover(Chromosome other) {
        return crossover((LineEquationChromosome) other, new LineEquationChromosome(x, y));
    }

    public Chromosome crossover(LineEquationChromosome other, LineEquationChromosome newChromosome) {
        newChromosome.A = (random.nextBoolean()) ? A : other.A;
        newChromosome.B = (random.nextBoolean()) ? B : other.B;
        newChromosome.C = (random.nextBoolean()) ? C : other.C;

        return newChromosome;
    }

    @Override
    public double getFitness() {
        double distance = Math.abs(A * x + B * y + C);
        return 1 / (1 + distance);
    }
}