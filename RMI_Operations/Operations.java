import java.rmi.*;

public interface Operations extends Remote {
    int add(int a, int b) throws RemoteException;
    int subtract(int a, int b) throws RemoteException;
    int multiply(int a, int b) throws RemoteException;
    int division(int a, int b) throws RemoteException;

    int square(int a) throws RemoteException;
    int squareRoot(int a) throws RemoteException;
    int power(int a, int b) throws RemoteException;
    boolean palindrome(String str) throws RemoteException;
    boolean isEqualString(String str1, String str2) throws RemoteException;

    double celsiusToFahrenheit(double celsius) throws RemoteException;
    double milesToKilometers(double miles) throws RemoteException;
    String nameAppendToHello(String name) throws RemoteException;
    String lexicallyLargestString(String str1, String str2) throws RemoteException;
    int countVowels(String str) throws RemoteException;
    int findFactorial(int n) throws RemoteException;
}
