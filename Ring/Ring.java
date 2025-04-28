import java.util.*;

public class Ring {
    
    static int max_processes, coordinator;
    static boolean[] processes;

    static ArrayList<Integer> pid;

    Ring(int max) {
        max_processes = max;
        coordinator = max;
        processes = new boolean[max];
        pid = new ArrayList<>();

        for(int i = 0; i < max; i++) {
            processes[i] = true;
            System.out.println("Process P "+ (i+1) + " is created");
        }

        System.out.println("Process P " + coordinator + " becomes the coordinator");
    }

    static void display() {

        for(int i = 0; i < max_processes; i++) {
            System.out.println("Process P "+ (i+1) + (processes[i] ? " [UP]" : " [DOWN]"));
        }

        System.out.println("Process P " + coordinator + " is the coordinator.");
    }

    static void bringUp(int process_id) {

        if(processes[process_id-1]) {
            System.out.println("Process P " + process_id + " is already [UP]");
        } else {
            processes[process_id-1] = true;
            System.out.println("Process P " + process_id + " is brought [UP]");
        }
    }

    static void bringDown(int process_id) {

        if(!processes[process_id - 1]) {
            System.out.println("Process P " + process_id + " is already [DOWN]");
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process P " + process_id + " is brought [DOWN]");
        }
    }

    static void startElection(int process_id) {

        if(!processes[process_id - 1]) {
            System.out.println("Process P " + process_id + " is DOWN. Cannot start election.");
            return;
        }

        System.out.println("Process P " + process_id + " starts an election.");

        pid.clear();

        int temp = process_id % max_processes;
        pid.add(process_id);

        while(temp != process_id - 1) {

            if(processes[temp]) {
                pid.add(temp + 1);
                System.out.println("Process P " + (temp + 1) + " is sending message : " + pid);
            }

            temp = (temp + 1) % max_processes;
        }

        coordinator = Collections.max(pid);

        System.out.println("New coordinator is Process P " + coordinator);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice, process_id;
        while(true) {

            System.out.println("------------------ RING ALGORITHM ------------------");
            System.out.println("1. Create Process");
            System.out.println("2. Display Process");
            System.out.println("3. Bring up a process");
            System.out.println("4. Bring down a process");
            System.out.println("5. Start Election");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch(choice) {
                case 1:
                {
                    System.out.print("Enter the number of processes: ");
                    max_processes = sc.nextInt();
                    new Ring(max_processes);
                    break;
                }
                case 2:
                {
                    display();
                    break;
                }
                case 3:
                {
                    System.out.print("Enter the process ID to bring up: ");
                    process_id = sc.nextInt();
                    bringUp(process_id);
                    break;
                }
                case 4:
                {
                    System.out.print("Enter the process ID to bring down: ");
                    process_id = sc.nextInt();
                    bringDown(process_id);
                    break;
                }
                case 5:
                {
                    System.out.print("Enter the process ID to start election: ");
                    process_id = sc.nextInt();
                    startElection(process_id);
                    break;
                }
                case 6:
                {
                    System.out.println("Exiting the program...");
                    return;
                }
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
