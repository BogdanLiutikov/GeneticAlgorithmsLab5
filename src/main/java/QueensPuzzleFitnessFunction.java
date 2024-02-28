import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

public class QueensPuzzleFitnessFunction implements FitnessEvaluator<QueensPuzzleSolution> {

    int dimension;
    int fit_count;

    public QueensPuzzleFitnessFunction() {
        fit_count = 0;
    }

    public double getFitness(QueensPuzzleSolution solution, List<? extends QueensPuzzleSolution> list) {
        fit_count++;
        List<Integer> field = solution.getField();
        this.dimension = field.size();
        double result = 0;
        int count = 0;

        for (int k = 0; k < 2 * dimension - 1; k++) {
            int rowStart = Math.max(0, k - dimension + 1);
            int colStart = Math.max(0, dimension - k - 1);
            int rowStartSecondary = Math.max(0, k - dimension + 1);
            int colStartSecondary = Math.min(k, dimension - 1);

            count = 0;
            while (rowStart < dimension && colStart < dimension) {
                if (field.get(rowStart) == colStart)
                    count++;
                rowStart++;
                colStart++;
            }
            if (count > 1)
                result += count;

            count = 0;
            while (rowStartSecondary < dimension && colStartSecondary >= 0) {
                if (field.get(rowStartSecondary) == colStartSecondary)
                    count++;
                rowStartSecondary++;
                colStartSecondary--;
            }
            if (count > 1)
                result += count;
        }

        return result;
    }

    public boolean isNatural() {
        return false;
    }
}
