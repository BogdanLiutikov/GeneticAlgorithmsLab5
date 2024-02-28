import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class QueensPuzzleFactory extends AbstractCandidateFactory<QueensPuzzleSolution> {

    int dimension;

    public QueensPuzzleFactory(int dimension) {
        this.dimension = dimension;
    }

    public QueensPuzzleSolution generateRandomCandidate(Random random) {
        QueensPuzzleSolution solution = new QueensPuzzleSolution(dimension, random);
        //your implementation
        return solution;
    }
}

