import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QueensPuzzleAlg {

    static double best_fit = Double.MAX_VALUE;
    static int best_gen = -1;
    static int fit_count = -1;
    static int DIM = 64;

    public static void main(String[] args) {
        int N = 31;
        double[] fitness = new double[N];
        int[] gen = new int[N];
        int[] fit_counts = new int[N];
        for (int i = 0; i < N; i++) {
            System.out.println("Тест " + (i + 1));
            best_fit = Double.MAX_VALUE;
            best_gen = -1;
            fit_count = 0;
            run(10000, -1, 1000);
            fitness[i] = best_fit;
            gen[i] = best_gen;
            fit_counts[i] = fit_count;
        }

        System.out.println("Среднее количество и стандартное отклонение итераций: avg=" + Arrays.stream(gen).average().getAsDouble() +
                " std=" +  calculateStandardDeviation(gen));
        System.out.println("Среднее количество и стандартное отклонение вызовов фитнес функций: avg=" + Arrays.stream(fit_counts).average().getAsDouble() +
                " std=" + calculateStandardDeviation(fit_counts));
    }

    public static double calculateStandardDeviation(int[] array) {
        double sum = 0.0;
        for (double i : array) {
            sum += i;
        }


        int length = array.length;
        double mean = sum / length;


        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    private static void run(int population, int generation, int elit) {
        int populationSize = population; // size of population
        int generations = generation; // number of generations


        Random random = new Random(); // random

        CandidateFactory<QueensPuzzleSolution> factory = new QueensPuzzleFactory(DIM); // generation of solutions
        QueensPuzzleFitnessFunction evaluator = new QueensPuzzleFitnessFunction(); // Fitness function

        List<EvolutionaryOperator<QueensPuzzleSolution>> operators = new ArrayList<EvolutionaryOperator<QueensPuzzleSolution>>();
        operators.add(new QueensPuzzleCrossover()); // Crossover
        operators.add(new QueensPuzzleMutation()); // Mutation
        EvolutionPipeline<QueensPuzzleSolution> pipeline = new EvolutionPipeline<QueensPuzzleSolution>(operators);

//        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator
        SelectionStrategy<Object> selection = new RankSelection(); // Selection operator
//        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.95)); // Selection operator


//        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
//                factory, pipeline, evaluator, selection, populationSize, false, random);
        EvolutionEngine<QueensPuzzleSolution> algorithm = new GenerationalEvolutionEngine<>(
                factory, pipeline, evaluator, selection, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                if (populationData.getGenerationNumber() % 500 == 0)
                    System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                QueensPuzzleSolution best = (QueensPuzzleSolution) populationData.getBestCandidate();
//                System.out.println("\tBest solution = \n" + best.toString());
                if (bestFit < best_fit) {
                    best_fit = bestFit;
                    best_gen = populationData.getGenerationNumber();
                }
            }
        });

//        TerminationCondition terminate = new GenerationCount(generations);
        TerminationCondition terminate = new TargetFitness(0, false);
        algorithm.evolve(populationSize, elit, terminate);
        fit_count = evaluator.fit_count;
        System.out.println(best_gen);
        System.out.println(best_fit);
    }
}
