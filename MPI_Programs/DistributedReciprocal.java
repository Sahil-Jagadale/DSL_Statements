import mpi.*;

public class DistributedReciprocal {
    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] numbers = new int[size];
        int[] number = new int[1]; // each process gets 1 number
        double[] reciprocal = new double[1];
        double[] resultArray = new double[size];

        // Root process generates array of numbers
        if (rank == 0) {
            for (int i = 0; i < size; i++) {
                numbers[i] = i + 1; // just for simplicity: 1, 2, 3, ...
                System.out.print(numbers[i] + " ");
            }
            System.out.println();
        }

        // Scatter numbers to each process
        MPI.COMM_WORLD.Scatter(numbers, 0, 1, MPI.INT,
                               number, 0, 1, MPI.INT, 0);

        // Each process calculates reciprocal
        reciprocal[0] = 1.0 / number[0];
        System.out.println("Process " + rank + " reciprocal: " + reciprocal[0]);

        // Gather reciprocals back to root
        MPI.COMM_WORLD.Gather(reciprocal, 0, 1, MPI.DOUBLE,
                              resultArray, 0, 1, MPI.DOUBLE, 0);

        // Root process displays final result array
        if (rank == 0) {
            System.out.println("Final Reciprocal Array:");
            for (int i = 0; i < size; i++) {
                System.out.print(resultArray[i] + " ");
            }
            System.out.println();
        }

        MPI.Finalize();
    }
}
