import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QueensPuzzleMutation implements EvolutionaryOperator<QueensPuzzleSolution> {
    public List<QueensPuzzleSolution> apply(List<QueensPuzzleSolution> population, Random random) {
        // your implementation:
        for (QueensPuzzleSolution candidate : population
        ) {
            for (int i = 0; i < candidate.dimension; i++) {
                if (random.nextDouble() < 1. / candidate.dimension) {
                    if (random.nextDouble() < 0.5)
                        swap(candidate, random);
                    else if (random.nextDouble() < 0.5)
                        inverse(candidate, random);
                    else if (random.nextDouble() < 0.5)
                        scramble(candidate, random);
                }
            }
        }
        return population;
    }

    private static void swap(QueensPuzzleSolution candidate, Random random) {
        int dim = candidate.dimension;
        int i1 = random.nextInt(dim);
        int i2 = random.nextInt(dim);
        List<Integer> filed = candidate.getField();
        int temp = filed.get(i1);
        filed.set(i1, filed.get(i2));
        filed.set(i2, temp);
    }

    private static void inverse(QueensPuzzleSolution candidate, Random random) {
        int dim = candidate.dimension;
        int i1 = random.nextInt(dim - 1);
        int i2 = i1 + random.nextInt(dim / 4) + 1;
        i2 = Math.min(i2, dim - 1);
        Collections.reverse(candidate.getField().subList(i1, i2 + 1));
    }

    private static void scramble(QueensPuzzleSolution candidate, Random random) {
        int dim = candidate.dimension;
        int i1 = random.nextInt(dim - 1);
        int i2 = i1 + random.nextInt(dim / 4) + 1;
        i2 = Math.min(i2, dim - 1);
        Collections.shuffle(candidate.getField().subList(i1, i2 + 1), random);
    }
}
