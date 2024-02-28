import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QueensPuzzleSolution {

    int dimension;
    List<Integer> field; // index = row, value = column;

    public List<Integer> getField() {
        return field;
    }

    public QueensPuzzleSolution(int size) {
        this.dimension = size;

        field = new ArrayList<Integer>(size);

        for (int i = 0; i < size; i++) {
            field.add(i);
        }

        Collections.shuffle(field);
    }

    public QueensPuzzleSolution(int dimension, Random random) {
        this.dimension = dimension;
        field = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            field.add(i);
        }
        Collections.shuffle(field, random);
    }

    public QueensPuzzleSolution(QueensPuzzleSolution parent) {
        this.dimension = parent.dimension;
        this.field = new ArrayList<>(parent.getField());
    }


    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (field.get(i) == j)
                    representation.append(" * ");
                else
                    representation.append(" • ");
            }
            representation.append('\n');
        }

        return representation.toString();
    }
}
