import mpi.*;
import java.util.Random;

public class DistributedAverage {
    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int numbersPerProcess = 5;
        int totalNumbers = numbersPerProcess * size;

        int[] numbers = new int[totalNumbers];
        int[] subset = new int[numbersPerProcess];
        double[] localAverage = new double[1];
        double[] allAverages = new double[size];

        // Root process generates random numbers
        if (rank == 0) {
            Random rand = new Random();
            System.out.println("Generated numbers:");
            for (int i = 0; i < totalNumbers; i++) {
                numbers[i] = rand.nextInt(100) + 1; // random 1 to 100
                System.out.print(numbers[i] + " ");
            }
            System.out.println();
        }

        // Scatter numbers to all processes
        MPI.COMM_WORLD.Scatter(numbers, 0, numbersPerProcess, MPI.INT,
                               subset, 0, numbersPerProcess, MPI.INT, 0);

        // Each process computes local average
        int localSum = 0;
        for (int i = 0; i < numbersPerProcess; i++) {
            localSum += subset[i];
        }
        localAverage[0] = (double) localSum / numbersPerProcess;
        System.out.println("Process " + rank + " average: " + localAverage[0]);

        // Gather all averages to root process
        MPI.COMM_WORLD.Gather(localAverage, 0, 1, MPI.DOUBLE,
                              allAverages, 0, 1, MPI.DOUBLE, 0);

        // Root process computes final average
        if (rank == 0) {
            double finalSum = 0.0;
            for (int i = 0; i < size; i++) {
                finalSum += allAverages[i];
            }
            double finalAverage = finalSum / size;
            System.out.println("Final average: " + finalAverage);
        }

        MPI.Finalize();
    }
}
