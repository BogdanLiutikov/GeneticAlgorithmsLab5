import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QueensPuzzleCrossover extends AbstractCrossover<QueensPuzzleSolution> {
    protected QueensPuzzleCrossover() {
        super(1);
    }

    protected List<QueensPuzzleSolution> mate(QueensPuzzleSolution p1, QueensPuzzleSolution p2, int i, Random random) {
        ArrayList<QueensPuzzleSolution> children = new ArrayList<>();
        // your implementation:
        QueensPuzzleSolution c1 = new QueensPuzzleSolution(p1);
        QueensPuzzleSolution c2 = new QueensPuzzleSolution(p2);

        orderCrossOver(p1, p2, c1, random);
        orderCrossOver(p2, p1, c2, random);

        children.add(c1);
        children.add(c2);
        return children;
    }

    private static void orderCrossOver(QueensPuzzleSolution mainParent, QueensPuzzleSolution secondParent, QueensPuzzleSolution child, Random random) {
        int dim = mainParent.dimension;

        int a = random.nextInt(dim - 1);
        int b = random.nextInt(dim - a) + a;

        Set<Integer> used = new HashSet<>();
        for (int i = a; i <= b; i++) {
            int cur = mainParent.getField().get(i);
            used.add(cur);
            child.getField().set(i, cur);
        }
        int insertIndex = (b + 1) % dim;
        for (int i = 0; i < dim; i++) {
            int curIndex = (b + 1 + i) % dim;
            int cur = secondParent.getField().get(curIndex);
            if (!used.contains(cur)) {
                child.getField().set(insertIndex, cur);
                insertIndex = (insertIndex + 1) % dim;
            }
        }
    }
}
