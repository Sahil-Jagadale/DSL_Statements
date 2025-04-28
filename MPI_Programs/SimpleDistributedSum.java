import mpi.*;

public class SimpleDistributedSum {
    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int unitSize = 5;
        int[] sendBuffer = new int[unitSize * size];
        int[] recvBuffer = new int[unitSize];

        if (rank == 0) {
            for (int i = 0; i < unitSize * size; i++) {
                sendBuffer[i] = i + 1;  // Fill array with 1 to N
            }
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, unitSize, MPI.INT,
                               recvBuffer, 0, unitSize, MPI.INT, 0);

        int localSum = 0;
        for (int i = 0; i < unitSize; i++) {
            localSum += recvBuffer[i];
        }

        System.out.println("Process " + rank + " local sum: " + localSum);

        int[] finalSum = new int[1];
        MPI.COMM_WORLD.Reduce(new int[]{localSum}, 0,
                              finalSum, 0, 1, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            System.out.println("Final sum: " + finalSum[0]);
        }

        MPI.Finalize();
    }
}

//Steps to run this program 
/*
 * FOR WINDOWS SYSTEMS:

 * 1) Unzip the "mpjexpress" into the "C drive" of the machine such that "C:\mpjexpress"
 * 2) Steps to run the code 
 *      2.1) Set Environment Variables (Temporary, in your Command Prompt of the system not in VScode)
 *          set MPJ_HOME=C:\mpjexpress
            set PATH=%PATH%;%MPJ_HOME%\bin
        2.2) Compile your code with MPJ library
            javac -cp .;%MPJ_HOME%\lib\mpj.jar SimpleDistributedSum.java
        2.3) Run using mpjrun
            mpjrun.bat -np 4 SimpleDistributedSum
            Here 4 means 4 process 
 */

/*
  * FOR LINUX SYSTEMS:

  * export MPJ_HOME=/home/ubuntu/Downloads/mpj-v0_44
  * export PATH=$MPJ_HOME/bin:$PATH
  * javac -cp $MPJ_HOME/lib/mpj.jar ArrSum.java
  * $MPJ_HOME/bin/mpjrun.sh -np 4 ArrSum
*/