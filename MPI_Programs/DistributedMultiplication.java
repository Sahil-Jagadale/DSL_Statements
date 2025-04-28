import mpi.*;

public class DistributedMultiplication {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] sendBuffer = new int[size];
        int[] recvBuffer = new int[1];  // Each process receives 1 element

        if (rank == 0) {
            // Initialize array at root
            for (int i = 0; i < size; i++) {
                sendBuffer[i] = (i + 1);  // e.g., 1, 2, 3, ..., size
            }
        }

        // Scatter one element to each process
        MPI.COMM_WORLD.Scatter(sendBuffer, 0, 1, MPI.INT, 
                               recvBuffer, 0, 1, MPI.INT, 0);

        // Each process multiplies received value by 2 (or any operation)
        int result = recvBuffer[0] * 2;

        // Print intermediate result from each process
        System.out.println("Process " + rank + " received " + recvBuffer[0] + " and computed " + result);

        MPI.Finalize();
    }
}
