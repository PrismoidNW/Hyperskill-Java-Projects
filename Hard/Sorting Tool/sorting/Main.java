package sorting;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static String inputFileName;
    private static String outputFileName;
    private static File fileOut;
    private static File fileIn;

    public static void main(String[] args) throws IOException {
        List<String> arguments = Arrays.stream(args).toList();
        boolean isNatural = arguments.contains("-sortingType") && arguments.contains("natural");
        boolean isCount = arguments.contains("-sortingType") && arguments.contains("byCount");


        boolean isNaturalInvalid = arguments.contains("-sortingType") && !arguments.contains("natural");
        boolean isCountInvalid = arguments.contains("-sortingType") && !arguments.contains("byCount");

        boolean hasInputFile = arguments.contains("-inputFile");
        boolean hasOutputFile = arguments.contains("-outputFile");
        if (hasInputFile) {
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).equals("-inputFile")) {
                    inputFileName = arguments.get(i + 1);
                    break;
                }
            }
            fileIn = new File(inputFileName);
        }
        if (hasOutputFile) {
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).equals("-outputFile")) {
                    outputFileName = arguments.get(i + 1);
                    break;
                }
            }
            fileOut = new File(outputFileName);
            fileOut.createNewFile();
        }

        String sortingType = isNatural || !isCount ? "NATURAL" : "BYCOUNT";

        if (isNaturalInvalid && isCountInvalid) {
            System.out.println("No sorting type defined!");
        }
        handleInvalidArg(arguments);

        boolean containsDataType = arguments.contains("-dataType");
        if (containsDataType) {
            if (!arguments.contains("long") && !arguments.contains("line") && !arguments.contains("word")) {
                System.out.println("No data type defined!");
            }
            if (arguments.contains("long")) {
                sortLong(sortingType);
            } else if (arguments.contains("line")) {
                sortLine(sortingType);
            } else {
                sortWord(sortingType);
            }
        }
        scanner.close();
        if (fileOut != null) {
        }

    }

    private static void handleInvalidArg(List<String> l) {
        int code = 0;
        for (String s : l) {
            if (code > 0) {
                code = 0;
                continue;
            }
            if (s.equals("-inputFile") || s.equals("-outputFile")) {
                code++;
            }
            if (hasInvalidArgs(s)) {
                System.out.println("\"" + s + "\" is not a valid parameter. It will be skipped");
                return;
            }
        }
    }

    private static boolean hasInvalidArgs(String args) {
        return !(args.equals("-sortingType") ||
                args.equals("-dataType") ||
                args.equals("natural") ||
                args.equals("byCount") ||
                args.equals("line") ||
                args.equals("word") ||
                args.equals("long") ||
                args.equals("-inputFile") ||
                args.equals("-outputFile"));
    }

    private static void sortLong(String sort) throws IOException {
        List<Long> numbers = new ArrayList<>();
        long greaterNumber = -1;
        int count = 0;
        if (inputFileName != null) {
            try (Scanner fileScanner = new Scanner(fileIn)) {
                while (fileScanner.hasNextLong()) {
                    long num = scanner.nextLong();
                    numbers.add(num);
                    if (num > greaterNumber) {
                        greaterNumber = num;
                    }
                }
            }
        } else {
            while (scanner.hasNextLong()) {
                long num = scanner.nextLong();
                numbers.add(num);

                if (num > greaterNumber) {
                    greaterNumber = num;
                }
            }
            System.out.printf("Total numbers: %d.\n", numbers.size());
        }
        List<String> longAsStr = numbers.stream().map(Object::toString).collect(Collectors.toList());
        count = Collections.frequency(numbers, greaterNumber);

        switch (sort) {
            case "NATURAL" -> {
                sortNatural(longAsStr, false);
            }
            case "BYCOUNT" -> {
                sortCount(longAsStr, false);
            }
            default -> {
                System.out.printf("The greatest number: %d (%d time(s), %d%%).\n", greaterNumber, count,
                        (count * 100 / numbers.size())
                );
            }
        }
    }

    private static void sortLine(String sort) throws IOException {
        List<String> lines = new ArrayList<>();
        String greatestLine = "";

        if (inputFileName != null) {
            try (Scanner fileScanner = new Scanner(fileIn)) {
                while (fileScanner.hasNextLong()) {
                    String line = scanner.nextLine();
                    lines.add(line);
                    if (line.length() > greatestLine.length()) {
                        greatestLine = line;
                    }
                }
            }
        } else {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
                if (line.length() > greatestLine.length()) {
                    greatestLine = line;
                }
            }
            System.out.printf("Total lines: %d.\n", lines.size());
        }
        int count = Collections.frequency(lines, greatestLine);

        switch (sort) {
            case "NATURAL" -> {
                sortNatural(lines, true);
            }
            case "BYCOUNT" -> {
                sortCount(lines, true);
            }
            default -> {
                System.out.println("The longest line:");
                System.out.println(greatestLine);
                System.out.printf("(%d time(s), %d%%).", count, (count * 100 / lines.size()));
            }
        }
    }

    private static void sortWord(String sort) throws IOException {
        List<String> words = new ArrayList<>();
        String greatestWord = "";

        if (inputFileName != null) {
            try (Scanner fileScanner = new Scanner(fileIn)) {
                while (fileScanner.hasNextLong()) {
                    String word = scanner.next();
                    words.add(word);
                    if (word.length() > greatestWord.length()) {
                        greatestWord = word;
                    }
                }
            }
        } else {
            while (scanner.hasNext()) {
                String line = scanner.next();
                words.add(line);
                if (line.length() > greatestWord.length()) {
                    greatestWord = line;
                }
            }
            System.out.printf("Total words: %d.\n", words.size());
        }
        int count = Collections.frequency(words, greatestWord);

        switch (sort) {
            case "NATURAL" -> {
                sortNatural(words, true);
            }
            case "BYCOUNT" -> {
                sortCount(words, true);
            }
            default -> {
                System.out.printf("The longest word: %s (%d time(s), %d%%).\n", greatestWord, count,
                        (count * 100 / words.size())
                );
            }
        }
    }

    private static void sortNatural(List<String> str, boolean isLine) throws IOException {
        if (isLine) {
            if (outputFileName == null) {
                System.out.print("Sorted data:");
                for (String s : str) {
                    System.out.println(s);
                }
            }
        } else {
            List<Long> num = str.stream().map(Long::parseLong).sorted().toList();
            System.out.print("Sorted data:");

            if (outputFileName == null) {
                for (long l : num) {
                    System.out.print(" " + l);
                }
            }
        }
    }

    private static void sortCount(List<String> str, boolean string) throws IOException {
        Collections.sort(str);

        if (string) {
            TreeMap<String, Integer> map = new TreeMap<>();

            for (String s : str) {
                if (map.containsKey(s)) continue;
                int count = Collections.frequency(str, s);
                map.put(s, count);
            }
            Stream<Entry<String, Integer>> sorted =
                    map.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue());

            if (outputFileName == null) {
                for (Entry<String, Integer> s : sorted.toList()) {
                    System.out.printf("%s: %d time(s), %d%%\n", s.getKey(), s.getValue(),
                            (s.getValue() * 100 / str.size())
                    );
                }
            }
        } else {
            TreeMap<Long, Integer> map = new TreeMap<>();

            for (String s : str) {
                if (map.containsKey(Long.parseLong(s))) continue;
                int count = Collections.frequency(str, s);
                map.put(Long.parseLong(s), count);
            }

            Stream<Entry<Long, Integer>> sorted =
                    map.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue());

            if (outputFileName == null) {
                for (Entry<Long, Integer> s : sorted.toList()) {
                    System.out.printf("%s: %d time(s), %d%%\n", s.getKey(), s.getValue(),
                            (s.getValue() * 100 / str.size())
                    );
                }
            }
        }
    }
}
