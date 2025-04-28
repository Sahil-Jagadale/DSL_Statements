import java.util.*;

public class Bully {

    static boolean[] processes;
    static int coordinator = -1;
    static int n;

    public static void createProcesses(int total) {

        n = total;
        processes = new boolean[n];
        for (int i = 0; i < n; i++) {
            processes[i] = true;
        }

        coordinator = n;
        System.out.println("Processes created. Coordinator is P : " + coordinator);
    }

    public static void displayProcesses() {

        for(int i = 0; i < n; i++) {
            System.out.println("Process P " + (i + 1) + " is " + (processes[i] ? "[UP]" : "[DOWN]") + " !!");
        }
        System.out.println("Current Process coordinator : P "+ coordinator);
    }

    public static void bringUp(int id) {

        if(processes[id - 1]) {
            System.out.println("Process P " + (id) + " is already [UP]");
        }
        else {
            processes[id - 1] = true;
            System.out.println("Process P " + (id) + " is brought [UP]");
        }
    }

    public static void bringDown(int id) {

        if(!processes[id - 1]) {
            System.out.println("Process P " + (id) + " is already [DOWN]");
        }
        else {
            processes[id - 1] = false;
            System.out.println("Process P "+ (id) + " is brought [DOWN]");
        }
    }

    public static void startElection(int id) {

        if(!processes[id - 1]) {
            System.out.println("Process P " + (id) + " is DOWN. Cannot start election.");
            return;
        }

        System.out.println("Process P " + id + " start an election");

        boolean higherProcessExists = false;

        for(int i = id; i < n; i++) {

            if(processes[i]) {
                
                higherProcessExists = true;
                System.out.println("Process P " + id + " sends Election message to Process P " + (i+1));

                System.out.println("Process P "+ (i+1) + " responds with OK to Process P " + id);
            }

        }

        if(!higherProcessExists) {

            coordinator = id;
            System.out.println("Process P " + id + " becomes the coordinator");

            for(int i = 0; i < n; i++) {
                if((i + 1) != id && processes[i]) {
                    System.out.println("Process P " + id + " sends COORDINATOR message to Process P " + (i+1));
                }
            }

        }
        else {
            System.out.println("Process P " + id + " waits for coordinator announcement");
        }

    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true) {

            int choice, id;
            System.out.println("----------------- Bully Algorithm -----------------");
            System.out.println("1. Create Processes");
            System.out.println("2. Display Processes");
            System.out.println("3. Bring up a process");
            System.out.println("4. Bring down a process");
            System.out.println("5. Start Election");
            System.out.println("6. Exit");
            System.out.println("Enter your choice : ");
            choice = sc.nextInt();
            
            switch(choice) {
                case 1: 
                {
                    System.out.println("Enter the number of processes : ");
                    int total = sc.nextInt();
                    createProcesses(total);
                    break;
                }
                case 2: 
                {
                    displayProcesses();
                    break;
                }
                case 3: 
                {
                    System.out.println("Enter the process number to bring up : ");
                    id = sc.nextInt();
                    if(id >= 1 && id <= n) bringUp(id);
                    else System.out.println("Invalid process number...");
                    break;
                }
                case 4:
                {
                    System.out.println("Enter the process number to bring down : ");
                    id = sc.nextInt();
                    if(id >= 1 && id <= n) bringDown(id);
                    else System.out.println("Invalid process number...");
                    break;
                }
                case 5:
                {
                    System.out.println("Enter the process number to start election : ");
                    id = sc.nextInt();
                    if(id >= 1 && id <= n) startElection(id);
                    else System.out.println("Invalid process number...");
                    break;
                }
                case 6:
                {
                    System.out.println("Exiting...");
                    return;
                }
                default:
                {
                    System.out.println("Invalid choice !!");
                }
            }
        }

    }
}
