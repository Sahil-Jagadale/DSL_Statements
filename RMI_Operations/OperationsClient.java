import java.rmi.*;
import java.util.Scanner;

public class OperationsClient {
    public static void main(String[] args) {
        try {
            // Connect to the remote service
            String serverAddress = "rmi://localhost:3031/OperationService";
            Operations stub = (Operations) Naming.lookup(serverAddress);

            Scanner scanner = new Scanner(System.in);
            int choice;
            
            do {
                // Display the menu
                System.out.println("\nMenu:");
                System.out.println("1. Add two numbers");
                System.out.println("2. Subtract two numbers");
                System.out.println("3. Multiply two numbers");
                System.out.println("4. Divide two numbers");
                System.out.println("5. Square a number");
                System.out.println("6. Square Root of a number");
                System.out.println("7. Power of a number");
                System.out.println("8. Check Palindrome");
                System.out.println("9. Compare two strings");
                System.out.println("10. Celsius to Fahrenheit");
                System.out.println("11. Miles to Kilometers");
                System.out.println("12. Append name to Hello");
                System.out.println("13. Find lexically largest string");
                System.out.println("14. Count vowels in a string");
                System.out.println("15. Find Factorial");
                System.out.println("16. Exit");
                System.out.print("Enter your choice (1-16): ");
                
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1: // Add
                        System.out.print("Enter first number: ");
                        int num1 = scanner.nextInt();
                        System.out.print("Enter second number: ");
                        int num2 = scanner.nextInt();
                        int resultAdd = stub.add(num1, num2);
                        System.out.println("Result: " + num1 + " + " + num2 + " = " + resultAdd);
                        break;
                    
                    case 2: // Subtract
                        System.out.print("Enter first number: ");
                        num1 = scanner.nextInt();
                        System.out.print("Enter second number: ");
                        num2 = scanner.nextInt();
                        int resultSub = stub.subtract(num1, num2);
                        System.out.println("Result: " + num1 + " - " + num2 + " = " + resultSub);
                        break;
                    
                    case 3: // Multiply
                        System.out.print("Enter first number: ");
                        num1 = scanner.nextInt();
                        System.out.print("Enter second number: ");
                        num2 = scanner.nextInt();
                        int resultMul = stub.multiply(num1, num2);
                        System.out.println("Result: " + num1 + " * " + num2 + " = " + resultMul);
                        break;

                    case 4: // Divide
                        System.out.print("Enter first number: ");
                        num1 = scanner.nextInt();
                        System.out.print("Enter second number: ");
                        num2 = scanner.nextInt();
                        double resultDiv = stub.division(num1, num2);
                        System.out.println("Result: " + num1 + " / " + num2 + " = " + resultDiv);
                        break;

                    case 5: // Square
                        System.out.print("Enter number to square: ");
                        num1 = scanner.nextInt();
                        int resultSquare = stub.square(num1);
                        System.out.println("Result: " + num1 + "^2 = " + resultSquare);
                        break;

                    case 6: // Square Root
                        System.out.print("Enter number to find square root: ");
                        num1 = scanner.nextInt();
                        double resultSqrt = stub.squareRoot(num1);
                        System.out.println("Result: sqrt(" + num1 + ") = " + resultSqrt);
                        break;

                    case 7: // Power
                        System.out.print("Enter base number: ");
                        num1 = scanner.nextInt();
                        System.out.print("Enter exponent: ");
                        int exp = scanner.nextInt();
                        int resultPower = stub.power(num1, exp);
                        System.out.println("Result: " + num1 + "^" + exp + " = " + resultPower);
                        break;

                    case 8: // Check Palindrome
                        System.out.print("Enter a string to check palindrome: ");
                        String inputString = scanner.nextLine();
                        boolean ans = stub.palindrome(inputString);
                        if(ans) {
                            System.out.println(inputString + " is a palindrome !!");
                        }
                        else {
                            System.out.println(inputString + " is not a palindrome !!");
                        }
                        break;

                    case 9: // Compare two strings
                        System.out.print("Enter first string: ");
                        String str1 = scanner.nextLine();
                        System.out.print("Enter second string: ");
                        String str2 = scanner.nextLine();
                        boolean ans1 = stub.isEqualString(str1, str2);

                        if(ans1) {
                            System.out.println(str1 + " and " + str2 + " are equal strings !!");
                        }
                        else {
                            System.out.println(str1 + " and " + str2 + " are not equal strings !!");
                        }
                        break;

                    case 10: // Celsius to Fahrenheit
                        System.out.print("Enter temperature in Celsius: ");
                        double celsius = scanner.nextDouble();
                        double fahrenheit = stub.celsiusToFahrenheit(celsius);
                        System.out.println(celsius + " Celsius = " + fahrenheit + " Fahrenheit");
                        break;

                    case 11: // Miles to Kilometers
                        System.out.print("Enter distance in miles: ");
                        double miles = scanner.nextDouble();
                        double kilometers = stub.milesToKilometers(miles);
                        System.out.println(miles + " miles = " + kilometers + " kilometers");
                        break;

                    case 12: // Append name to Hello
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        String greeting = stub.nameAppendToHello(name);
                        System.out.println("Greeting: " + greeting);
                        break;

                    case 13: // Lexically largest string
                        System.out.print("Enter first string: ");
                        String str3 = scanner.nextLine();
                        System.out.print("Enter second string: ");
                        String str4 = scanner.nextLine();
                        String largest = stub.lexicallyLargestString(str3, str4);
                        System.out.println("Lexically largest string: " + largest);
                        break;

                    case 14: // Count vowels
                        System.out.print("Enter a string: ");
                        String input = scanner.nextLine();
                        int count = stub.countVowels(input);
                        System.out.println("Number of vowels: " + count);
                        break;

                    case 15: // Find Factorial
                        System.out.print("Enter a number to find its factorial: ");
                        int factNum = scanner.nextInt();
                        int factorial = stub.findFactorial(factNum);
                        System.out.println("Factorial of " + factNum + " = " + factorial);
                        break;

                    case 16: // Exit
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice! Please choose a valid option.");
                }
            } while (choice != 16);
            
            scanner.close();
            
        } catch (NotBoundException e) {
            System.out.println("NotBoundException: " + e);
        } catch (Exception e) {
            System.out.println("Client Exception: " + e);
        }
    }
}
