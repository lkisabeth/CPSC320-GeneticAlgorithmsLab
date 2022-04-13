import java.util.Random;

public class BinaryChromosome implements Chromosome {
    protected int[] bits;
    private final Random random = new Random ();

    public BinaryChromosome(int numbits) { bits = new int[numbits]; }

    public void randomize() { for (int i = 0; i < bits.length; i++) bits[i] = random.nextInt(2); }

    public int[] getBits() { return bits; }

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

        System.arraycopy(bits, 0, newChromosome.bits, 0, first);

        System.arraycopy(other.bits, first, newChromosome.bits, first, second - first);

        if (bits.length - second >= 0) System.arraycopy(bits, second, newChromosome.bits, second, bits.length - second);

        return newChromosome;
    }

    @Override
    public double getFitness() {
        // calculate the maximum integer the given number of bits can represent
        int maxInt = (int)Math.pow(2, bits.length);

        // convert this chromosome's array of bits into the integer it represents (using provided .getNumber function)
        int thisInt = this.getNumber(0, bits.length-1);

        // return how close thisInt is to maxInt as a percentage, which represents the fitness of this particular chromosome. (0 being worst, 1 being exactly the maximum)
        return thisInt/(double)maxInt;
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