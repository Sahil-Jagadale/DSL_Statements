import java.rmi.*;
import java.rmi.server.*;

public class OperationsImpl extends UnicastRemoteObject implements Operations {
    protected OperationsImpl() throws RemoteException {
        super();
    }

    public int add(int a, int b) throws RemoteException {
        System.out.println("Adding " + a + " + " + b);
        return a + b;
    }

    public int subtract(int a, int b) throws RemoteException {
        System.out.println("Subtracting " + a + " - " + b);
        return a - b;
    }
    public int multiply(int a, int b) throws RemoteException {
        System.out.println("Multiplying " + a + " * " + b);
        return a * b;
    }

    public int division(int a, int b) throws RemoteException {
        System.out.println("Dividing " + a + " / " + b);
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    public int square(int a) throws RemoteException {
        System.out.println("Squaring " + a);
        return a * a;
    }

    public int squareRoot(int a) throws RemoteException {
        System.out.println("Square root of " + a);
        if (a < 0) {
            throw new ArithmeticException("Square root of negative number");
        }
        return (int) Math.sqrt(a);
    }

    public int power(int a, int b) throws RemoteException {
        System.out.println("Power " + a + " ^ " + b);
        return (int) Math.pow(a, b);
    }

    public boolean palindrome(String str) throws RemoteException {
        System.out.println("Checking if " + str + " is a palindrome");
        String reversed = new StringBuilder(str).reverse().toString();
        if (str.equals(reversed)) {
            System.out.println(str + " is a palindrome");
            return true;
        } else {
            System.out.println(str + " is not a palindrome");
            return false;
        }
    }

    public boolean isEqualString(String str1, String str2) throws RemoteException {
        System.out.println("Comparing " + str1 + " and " + str2);
        if (str1.equals(str2)) {
            System.out.println(str1 + " is equal to " + str2);
            return true;
        } else {
            System.out.println(str1 + " is not equal to " + str2);
            return false;
        }
    }

    public double celsiusToFahrenheit(double celsius) throws RemoteException {
        System.out.println("Converting " + celsius + " Celsius to Fahrenheit");
        return (celsius * 9/5) + 32;
    }

    public double milesToKilometers(double miles) throws RemoteException {
        System.out.println("Converting " + miles + " miles to kilometers");
        return miles * 1.60934;
    }

    public String nameAppendToHello(String name) throws RemoteException {
        System.out.println("Appending " + name + " to Hello");
        return "Hello " + name;
    }
    public String lexicallyLargestString(String str1, String str2) throws RemoteException {
        System.out.println("Finding lexically largest string between " + str1 + " and " + str2);
        return (str1.compareTo(str2) > 0) ? str1 : str2;
    }

    public int countVowels(String str) throws RemoteException {
        System.out.println("Counting vowels in " + str);
        int count = 0;
        for (char c : str.toCharArray()) {
            if ("aeiouAEIOU".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    public int findFactorial(int n) throws RemoteException {
        System.out.println("Finding factorial of " + n);
        if (n < 0) {
            throw new ArithmeticException("Factorial of negative number");
        }
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
