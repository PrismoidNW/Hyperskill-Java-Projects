package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        welcomeUser();
        requestInput();
    }

    private static void requestInput() {
        System.out.println("Welcome to Amazing Numbers!");
        while (true) {
            System.out.print("\nEnter a request: ");
            String input = scan.nextLine();
            String[] searchQueryArray = input.split(" ");
            System.out.println();
            if (input.trim().length() == 0) {
                welcomeUser();
                continue;
            }

            if (Long.parseLong(searchQueryArray[0]) == 0) break;

            switch (searchQueryArray.length) {
                case 1: {
                    NumberProcessor.processOneNumber(Long.parseLong(searchQueryArray[0]));
                    break;
                }
                case 2: {
                    NumberProcessor.processConsecutiveList(
                            Long.parseLong(searchQueryArray[0]),
                            Long.parseLong(searchQueryArray[1])
                    );
                    break;
                }
                default:
                    NumberProcessor.processQuery(Long.parseLong(searchQueryArray[0]),
                            Long.parseLong(searchQueryArray[1]), searchQueryArray
                    );
            }
        }
        System.out.println("Goodbye!");
    }

    public static void welcomeUser() {
        System.out.println("\nSupported requests:");
        System.out.println("- enter a natural number to know it's properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate parameters with one space;");
        System.out.println("- enter 0 to exit.");
    }
}

class ErrorHandler {

    static final String VALID_PROPERTIES = "even odd buzz duck spy palindromic gapful square sunny jumping happy sad " +
            "-even -odd -buzz -duck -spy -palindromic -gapful -square -sunny -jumping -happy -sad";

    public static boolean checkNumbersErrors(long number1, long number2) { // return true if there are any errors
        boolean flag = false;
        if (number1 < 0) {
            System.out.println("The first parameter should be a natural number or zero.");
            flag = true;
        }
        if (number2 < 1) {
            System.out.println("The second parameter should be a natural number");
            flag = true;
        }
        return flag;
    }

    public static boolean checkProperties(String[] searchProperties) {
        String[] invalidProperties = new String[searchProperties.length];
        int numberOfInvalidProperties = getNumberOfInvalidProperties(searchProperties, invalidProperties);

        if (numberOfInvalidProperties == 1) {
            System.out.println("The property [" + invalidProperties[0] + "] is wrong.");
            printAvailableProperties();
            return true;
        } else if (numberOfInvalidProperties > 1) {
            System.out.println("The properties " + Arrays.toString(invalidProperties) + " are wrong.");
            printAvailableProperties();
            return true;
        }

        if (searchProperties.length > 1) {
            return areMutuallyExclusive(searchProperties);
        }

        return false;
    }

    private static int getNumberOfInvalidProperties(String[] searchProperties, String[] invalidProperties) {
        int numberOfInvalidProperties = 0;
        for (String searchProperty : searchProperties) {
            if (!(isParameterValid(searchProperty))) {
                invalidProperties[numberOfInvalidProperties] = searchProperty.toUpperCase();
                numberOfInvalidProperties++;
            }
        }
        return numberOfInvalidProperties;
    }

    private static void printAvailableProperties() {
        System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, " +
                "JUMPING, HAPPY, SAD]");
    }

    private static boolean areMutuallyExclusive(String[] searchProperties) {
        if (isContain(searchProperties, "EVEN") && isContain(searchProperties, "ODD") || (isContain(
                searchProperties,
                "-EVEN"
        ) && isContain(searchProperties, "-ODD"))) {
            System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]");
            System.out.println("There are no numbers with these properties.");
            return true;
        }
        if (isContain(searchProperties, "SUNNY") && isContain(searchProperties, "SQUARE") || (isContain(searchProperties, "-SUNNY") && isContain(searchProperties, "-SQUARE"))) {
            System.out.println("The request contains mutually exclusive properties: [SUNNY, SQUARE]");
            System.out.println("There are no numbers with these properties.");
            return true;
        }
        if (isContain(searchProperties, "DUCK") && isContain(searchProperties, "SPY") || (isContain(
                searchProperties,
                "-DUCK"
        ) && isContain(searchProperties, "-SPY"))) {
            System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
            System.out.println("There are no numbers with these properties.");
            return true;
        }
        if (isContain(searchProperties, "HAPPY") && isContain(searchProperties, "SAD") || (isContain(searchProperties
                , "-HAPPY") && isContain(searchProperties, "-SAD"))) {
            System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]");
            System.out.println("There are no numbers with these properties.");
            return true;
        }

        return findOppositeProperties(searchProperties);
    }

    private static boolean findOppositeProperties(String[] searchProperties) {
        String[] oppositeProperties = new String[2];
        int k = 0;

        for (String i : searchProperties) {
            for (String j : searchProperties) {
                if (i.equalsIgnoreCase(oppositePair(j))) {
                    oppositeProperties[k++] = i;
                    break;
                }
            }
        }

        if (k == 2) {
            System.out.println("The request contains mutually exclusive properties: " + Arrays.toString(oppositeProperties).toUpperCase());
            System.out.println("There are no numbers with these properties.");
            return true;
        }

        return false;
    }

    private static boolean isParameterValid(String searchParameter) { // checks if searched property is valid, does
        // it exist
        return VALID_PROPERTIES.contains(searchParameter) || VALID_PROPERTIES.toUpperCase().contains(searchParameter);
    }

    private static boolean isContain(String[] source, String subItem) {
        boolean hasTerm = false;
        for (String word : source) {
            if (word.toLowerCase().equals(subItem.toLowerCase())) {
                hasTerm = true;
                break;
            }
        }
        return hasTerm;
    }

    private static String oppositePair(String property) {
        if (property.startsWith("-"))
            return property.replace("-", "");

        return "-" + property;
    }
}

class AmazingNumbers {

    public static boolean checkHappy(long number) {

        ArrayList<Long> sequence = new ArrayList<>();
        sequence.add(number);

        if (number != 1) {
            for (int i = 0; i < sequence.size(); i++) {
                long sum = 0;
                if (sequence.get(i) == 1) {
                    return true;
                }

                long check = sequence.get(i);
                while (check != 0) {
                    sum += Math.pow(check % 10, 2);
                    check /= 10;
                }

                if (sum == 145 || sum == 3 || sum == 4 || sum == 5 || sum == 6) {
                    return false;
                }

                sequence.add(sum);
            }
        }
        return true;
    }

    public static boolean checkJumping(long number) {
        ArrayList<Long> digits = new ArrayList<>();

        while (number != 0) {
            digits.add(number % 10);
            number /= 10;
        }

        return isFlagJumping(digits);
    }

    private static boolean isFlagJumping(ArrayList<Long> digits) {
        boolean flag = true;
        for (int i = 0; i < digits.size() - 1; i++) {
            if (Math.abs(digits.get(i) - digits.get(i + 1)) != 1) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static boolean checkGapful(long number) {
        if (!(number < 100)) {
            String numberToString = Long.toString(number);
            String firstLastDigit =
                    numberToString.charAt(0) + String.valueOf(numberToString.charAt(numberToString.length() - 1));
            long divisor = Long.parseLong(firstLastDigit);
            return number % divisor == 0;
        }
        return false;
    }

    public static boolean checkPalindrome(long number) {
        long reversedNumber = 0;
        long check = number;
        while (check != 0) {
            long remainder = check % 10;
            reversedNumber = reversedNumber * 10 + remainder;
            check /= 10;
        }
        return reversedNumber == number;
    }

    public static boolean checkSpy(long number) { // if the sum of all digits is equal to the product of all digits
        long sum = 0;
        long product = 1;
        while (number != 0) {
            long digit = number % 10;
            sum += digit;
            product *= digit;
            number /= 10;
        }
        return sum == product;
    }

    public static boolean checkSquare(long number) {
        return number == ((long) Math.sqrt(number) * Math.sqrt(number));
    }

    public static boolean checkParity(long number) {
        return number % 2 == 0;
    }

    public static boolean checkBuzz(long number) {
        return ((number % 10) == 7) || number % 7 == 0;
    }

    public static boolean checkDuck(long number) {
        return Long.toString(number).substring(1).contains("0");
    }
}

class NumberProcessor {
    public static void processOneNumber(long number) {
        if (!(number < 0)) {
            System.out.printf("Properties of %,d%n", number);
            System.out.println("        buzz: " + AmazingNumbers.checkBuzz(number));
            System.out.println("        duck: " + AmazingNumbers.checkDuck(number));
            System.out.println(" palindromic: " + AmazingNumbers.checkPalindrome(number));
            System.out.println("      gapful: " + AmazingNumbers.checkGapful(number));
            System.out.println("         spy: " + AmazingNumbers.checkSpy(number));
            System.out.println("      square: " + AmazingNumbers.checkSquare(number));
            System.out.println("       sunny: " + AmazingNumbers.checkSquare(number + 1));
            System.out.println("     jumping: " + AmazingNumbers.checkJumping(number));
            System.out.println("       happy: " + AmazingNumbers.checkHappy(number));
            System.out.println("         sad: " + !AmazingNumbers.checkHappy(number));
            System.out.println("        even: " + AmazingNumbers.checkParity(number));
            System.out.println("         odd: " + !AmazingNumbers.checkParity(number));
        } else {
            System.out.println("The first parameter should be a natural number or zero.");
        }
    }

    public static void processConsecutiveList(long startingNumber, long consecutiveNumber) { // for two numbers
        if (ErrorHandler.checkNumbersErrors(startingNumber, consecutiveNumber))
            return; // return if there are any errors
        for (long i = startingNumber; i < startingNumber + consecutiveNumber; i++) {
            StringBuilder numberProperties = checkNumberProperties(i);
            System.out.printf("\t\t\t %,1d is %s%n", i, numberProperties.substring(0, numberProperties.length() - 2));
        }
    }

    public static void processQuery(long startingNumber, long howManyNumbers, String[] searchQuery) { // for
        // parameters and a property
        String[] searchParametersArray = Arrays.copyOfRange(searchQuery, 2, searchQuery.length);
        if (ErrorHandler.checkNumbersErrors(startingNumber, howManyNumbers)) return; // return if there are any errors
        if (ErrorHandler.checkProperties(searchParametersArray))
            return; // return if there are any error within properties
        printQuery(startingNumber, howManyNumbers, searchParametersArray);
    }

    private static void printQuery(long startingNumber, long howManyNumbers, String[] searchParametersArray) {
        int x = 0;
        while (x < howManyNumbers) {
            StringBuilder numberProperties = checkNumberProperties(startingNumber);

            if (checkParameter(searchParametersArray, numberProperties)) { // there is a property / properties in the
                // number
                System.out.printf("\t\t\t %,1d is %s%n", startingNumber, numberProperties.substring(
                        0,
                        numberProperties.length() - 2
                ));
                x++;
            }
            startingNumber++;
        }
    }

    private static boolean checkParameter(String[] searchParameter, StringBuilder numberProperties) { // checks if
        // searched property is available in numberProperties
        boolean flag = true;
        for (String property : searchParameter) {
            if (!property.startsWith("-")) {
                if (!(numberProperties.toString().contains(property.toLowerCase()))) {
                    flag = false;
                    break;
                }
            } else {
                if (numberProperties.toString().contains(property.replace("-", "").toLowerCase())) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private static StringBuilder checkNumberProperties(long i) {
        StringBuilder numberProperties = new StringBuilder();
        if (AmazingNumbers.checkBuzz(i)) numberProperties.append("buzz, ");
        if (AmazingNumbers.checkDuck(i)) numberProperties.append("duck, ");
        if (AmazingNumbers.checkPalindrome(i)) numberProperties.append("palindromic, ");
        if (AmazingNumbers.checkGapful(i)) numberProperties.append("gapful, ");
        if (AmazingNumbers.checkSpy(i)) numberProperties.append("spy, ");
        if (AmazingNumbers.checkSquare(i)) numberProperties.append("square, ");
        if (AmazingNumbers.checkJumping(i)) numberProperties.append("jumping, ");
        if (AmazingNumbers.checkHappy(i)) numberProperties.append("happy, ");
        if (!AmazingNumbers.checkHappy(i)) numberProperties.append("sad, ");
        if (AmazingNumbers.checkSquare(i + 1)) numberProperties.append("sunny, ");
        if (AmazingNumbers.checkParity(i)) numberProperties.append("even, ");
        else numberProperties.append("odd, ");
        return numberProperties;
    }
}