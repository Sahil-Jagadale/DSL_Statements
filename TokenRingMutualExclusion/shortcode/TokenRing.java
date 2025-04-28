import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TokenRing {

    static int myId;
    static int nextId;
    static String myIP;
    static int myPort;
    static String nextIP;
    static int nextPort;
    static boolean hasToken = false;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter My ID (0 or 1): ");
            myId = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter My IP: ");
            myIP = sc.nextLine();

            System.out.print("Enter My Port: ");
            myPort = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter Next Machine's IP: ");
            nextIP = sc.nextLine();

            System.out.print("Enter Next Machine's Port: ");
            nextPort = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Do you initially have the token? (yes/no): ");
            String tokenInput = sc.nextLine();
            hasToken = tokenInput.equalsIgnoreCase("yes");

            sc.close();

            ServerSocket serverSocket = new ServerSocket(myPort);

            if (hasToken) {
                performCriticalSection();
                sendToken();
            }

            while (true) {
                System.out.println("Waiting for token...");
                Socket socket = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = br.readLine();
                if (msg.equals("TOKEN")) {
                    System.out.println("Token received.");
                    performCriticalSection();
                    sendToken();
                }
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void performCriticalSection() {
        System.out.println("Entering Critical Section...");
        try {
            Thread.sleep(3000); // Simulate some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting Critical Section...");
    }

    static void sendToken() {
        try {
            Socket s = new Socket(nextIP, nextPort);
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            pw.println("TOKEN");
            System.out.println("Token sent to next machine.");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*
 * Correct Order	Action
Step 1	Open two terminals
Step 2	First start Machine 1 (Receiver side first!) → so port 6000 is open
Step 3	Then start Machine 0 (Sender side)

Input 
Terminal 1:

javac TokenRing.java

java TokenRing

Enter:

My ID: 1

My IP: 127.0.0.1

My Port: 6000

Next Machine's IP: 127.0.0.1

Next Machine's Port: 5000

Initially have token: no

→ It will say Waiting for token...

Terminal 2:

javac TokenRing.java

java TokenRing

Enter:

My ID: 0

My IP: 127.0.0.1

My Port: 5000

Next Machine's IP: 127.0.0.1

Next Machine's Port: 6000

Initially have token: yes

→ It will Enter Critical Section and pass token to Machine 1.
 */