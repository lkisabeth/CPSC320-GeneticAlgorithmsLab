import java.util.Random;

public class BinaryChromosome implements Chromosome {
    protected int [] bits;
    private Random random = new Random ();

    public BinaryChromosome (int numbits) {
        bits = new int [numbits];
    }

    public void randomize () {
        for (int i = 0; i < bits.length; i++) bits[i] = random.nextInt(2);
    }

    @Override
    public void mutate(double mutationRate) {
        for (int i = 0; i < bits.length; i++) {
            if (random.nextDouble() <= mutationRate) bits[i] = (bits[i] == 1) ? 0 : 1;
        }
    }

    @Override
    public Chromosome crossover(Chromosome other) {
        return crossover ((BinaryChromosome) other, new BinaryChromosome (bits.length));
    }

    public Chromosome crossover(BinaryChromosome other, BinaryChromosome newChromosome) {
        int first = random.nextInt(bits.length);
        int second = random.nextInt(bits.length);

        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }

        for (int i = 0; i < first; i++) newChromosome.bits[i] = bits[i];

        for (int i = first; i < second; i++) newChromosome.bits[i] = other.bits[i];

        for (int i = second; i < bits.length; i++) newChromosome.bits[i] = bits[i];

        return newChromosome;
    }

    @Override
    public double getFitness() {
        // calculate the maximum integer the given number of bits can represent
        int maxInt = (2^bits.length) - 1;

        // convert this chromosome's array of bits into the integer it represents (using provided .getNumber function)
        int thisInt = this.getNumber(bits[0], bits[bits.length - 1]);
        // could also be written: int thisInt = Integer.parseInt(String.valueOf(bits), 2);

        // check how close thisInt is to maxInt as a percentage, which represents the fitness of this particular chromosome. (0 being worst, 1 being exactly the maximum)
        double fitness = thisInt/maxInt;
        return fitness;
    }

    public int getNumber (int first, int last) {
        int result = 0;
        int placeValue = 1;

        for (int i = last; i >= first; i--) {
            result += (bits[i] * placeValue);
            placeValue *= 2;
        }

        return result;
    }
}