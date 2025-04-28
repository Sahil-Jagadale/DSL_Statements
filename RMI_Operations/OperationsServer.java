import java.rmi.*;
import java.rmi.registry.*;

public class OperationsServer {
    public static void main(String[] args) {
        try {
            OperationsImpl obj = new OperationsImpl();
            LocateRegistry.createRegistry(3031);
            Naming.rebind("rmi://localhost:3031/OperationService", obj);
            System.out.println("Operations Server is Ready...");
        } catch (Exception e) {
            System.out.println("Server Exception: " + e);
        }
    }
}
