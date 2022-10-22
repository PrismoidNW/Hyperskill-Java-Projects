package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String totalChoices = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static String code = "";
    private static Integer possibleSymbols;
    private static Integer codeLength;

    public static void main(String[] args) {
        String oneLiner = """
                  I want to be\
                  \son one line please!
                  """;
        System.out.println(oneLiner);
    }

    private static boolean shouldExit(){
        return ((possibleSymbols == null) || (codeLength == null)) || (((codeLength > 36) || (codeLength < 1)) || (possibleSymbols < codeLength) || (possibleSymbols > 36));
    }

    public static void getInput() {
        String tempIn1 = scanner.nextLine();

        try {
            codeLength = Integer.parseInt(tempIn1);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + tempIn1 + "\" isn't a valid number. ");
            System.exit(0);
        }

        if (codeLength > 36 || codeLength < 1) {
            System.out.println("Error: can't generate a secret number with a length of " + codeLength + " because there aren't enough" +
                    " unique digits.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        tempIn1 = scanner.nextLine();

        try {
            possibleSymbols = Integer.parseInt(tempIn1);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + tempIn1 + "\" isn't a valid number. ");
            return;
        }

        if (possibleSymbols < codeLength) {
            System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with "
                    + possibleSymbols + " unique symbols.");
           return;
        }

        if (possibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        int lettersPossible = possibleSymbols;
        System.out.println("The secret is prepared: " + "*".repeat(codeLength) + " (0-9, " + totalChoices.charAt(10) + "-" + totalChoices.charAt(lettersPossible - 1) + ").");
        generateRandomCode(codeLength, lettersPossible);
    }

    private static void generateRandomCode(int max, int lettersPossible) {
        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        String subString = totalChoices.substring(0, lettersPossible);
        for (int i = 0; i < possibleSymbols; i++) {
            if (builder.length() == max) {
                break;
            }

            char charPicked = subString.charAt(r.nextInt(subString.length()));
            if (!builder.toString().contains(charPicked + "")) {
                builder.append(charPicked);
            } else {
                i -= 1;
            }
        }
        code = builder.toString();
    }

    private static String grade(String input) {
        String grade = "";

        int cows = 0;
        int bulls = 0;

        for (int i = 0; i < code.length(); i++) {
            if (i >= input.length()) {
                break;
            }
            if (code.charAt(i) == input.charAt(i)) {
                bulls++;
            } else if (code.contains(input.charAt(i) + "")) {
                cows++;
            }
        }

        if (bulls != 0) {
            if (bulls == 1) {
                grade += bulls + " bull";
            } else {
                grade += bulls + " bulls";
            }
        }

        if (cows != 0 && bulls != 0) {
            grade += " and ";
        }
        if (cows == 0 && bulls == 0) {
            grade += "None";
        }

        if (cows != 0) {
            if (cows == 1) {
                grade += cows + " cow";
            } else {
                grade += cows + " cows";
            }
        }
        grade += ".";
        return grade;
    }

    private static boolean isCorrect(String input) {
        return code.equals(input);
    }
}
