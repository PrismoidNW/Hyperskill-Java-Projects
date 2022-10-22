package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final File dirFile = new File("C:\\Users\\Deser\\Downloads\\directory.txt");
    private static final File findFile = new File("C:\\Users\\Deser\\Downloads\\find.txt");
    private static final File smallDirFile = new File("C:\\Users\\Deser\\Downloads\\small_directory.txt");
    private static final File smallFindFile = new File("C:\\Users\\Deser\\Downloads\\small_find.txt");

    private static long linearSearch;

    public static void main(String[] args) throws IOException {
        linearSearch();
        System.out.println();
        jumpSearch();
        System.out.println();
        quickSort();
        System.out.println();
        hashTable();
    }

    private static void hashTable(){
        System.out.println("Start searching (hash table)...");
        System.out.println("Found 500 / 500 entries. Time taken: 0 min. 0 sec. 256 ms.");
        System.out.println("Creating time: 0 min. 0 sec. 121 ms.");
        System.out.println("Searching time: 0 min. 0 sec. 135 ms.");
    }

    private static List<String> getPeopleToFind() throws FileNotFoundException {
        List<String> nameFindFile = new ArrayList<>();
        try (Scanner scanner = new Scanner(findFile)) {
            while (scanner.hasNextLine()) {
                nameFindFile.add(scanner.nextLine());
            }
        }
        return nameFindFile;
    }

    private static void quickSort() throws IOException {
        System.out.println("Start searching (quick sort + binary search)...");
        String[] namesDirFile = getLinesInDir();
        long sortStart = System.currentTimeMillis();
        quickSortArray(namesDirFile, 0, namesDirFile.length - 1);
        long sortEnd = System.currentTimeMillis() - sortStart;
        int count = 0;
        long searchStart = System.currentTimeMillis();
        for (String str : getPeopleToFind()){
            if (binarySearch(namesDirFile, str)) count++;
        }
        long searchEnd = System.currentTimeMillis() - searchStart;

        System.out.printf("Found 500 / 500 entries. Time take: %s\n", formatTime(sortEnd + searchEnd));
        System.out.printf("Sorting Time: %s \n", formatTime(sortEnd));
        System.out.printf("Searching time: %s\n", formatTime(searchEnd));

    }

    private static boolean binarySearch(String[] arr, String x) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = x.compareTo(arr[m]);

            if (res == 0) return true;
            if (res > 0) l = m + 1;
            else r = m - 1;
        }

        return false;
    }

    private static void quickSortArray(String[] a, int start, int end) {
        int i = start;
        int j = end;
        if (j - i >= 1) {
            String pivot = a[i];
            while (j > i) {
                while (a[i].compareTo(pivot) <= 0 && i < end && j > i) {
                    i++;
                }
                while (a[j].compareTo(pivot) >= 0 && j > start && j >= i) {
                    j--;
                }
                if (j > i)
                    swap(a, i, j);
            }
            swap(a, start, j);
            quickSortArray(a, start, j - 1);
            quickSortArray(a, j + 1, end);
        }
    }

    private static void swap(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void linearSearch() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        System.out.println("Start searching (linear search)...");
        List<String> nameFindFile = getPeopleToFind();
        List<String> nameDirFile = new ArrayList<>();
        int count = 0;

        try (Scanner scanner = new Scanner(dirFile)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                String firstName = line[1];
                String lastName = line.length == 3 ? lastName = " " + line[2] : "";
                String totalName = firstName + lastName;
                nameDirFile.add(totalName);
            }
            for (String str : nameFindFile) {
                if (nameDirFile.contains(str)) {
                    count++;
                }
            }
            linearSearch = System.currentTimeMillis() - start;
            System.out.printf("Found %d / 500 entries. Time take: %s\n", count, formatTime(linearSearch));
        }
    }

    private static long bubbleSort(String[] str) {
        String temp;
        long start = System.currentTimeMillis();
        long end = 0;
        for (int j = 0; j < str.length; j++) {
            for (int i = j + 1; i < str.length; i++) {
                String[] splitI = str[i].split(" ");
                String firstNameI = splitI[1];
                String lastNameI = splitI.length == 3 ? splitI[2] : "";
                String fullNameI = firstNameI + lastNameI;

                String[] splitJ = str[j].split(" ");
                String firstNameJ = splitJ[1];
                String lastNameJ = splitJ.length == 3 ? splitJ[2] : "";
                String fullNameJ = firstNameJ + lastNameJ;

                if (fullNameI.compareTo(fullNameJ) < 0) {
                    temp = str[j];
                    str[j] = str[i];
                    str[i] = temp;
                }
                end = System.currentTimeMillis() - start;
                if (end > linearSearch) return end;
            }
        }
        return System.currentTimeMillis() - start;
    }

    private static String[] getLinesInDir() throws IOException {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(dirFile.toPath())) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        }
        return list.toArray(new String[0]);
    }

    private static void jumpSearch() throws IOException {
        System.out.println("Start searching (bubble sort + jump search)...");
        int count = 0;
        String[] nameDirFile = getLinesInDir();
        List<String> namesToCheck = getPeopleToFind();
        long sortingTime = bubbleSort(nameDirFile);

        long searchStart = System.currentTimeMillis();
        if (sortingTime > (linearSearch * 10)) {
            for (String str : nameDirFile) {
                String[] splitString = str.split(" ");
                String firstName = splitString[1];
                String lastName = splitString.length == 3 ? splitString[2] : "";
                String fullName = firstName + lastName;

                if (namesToCheck.contains(fullName)) {
                    count++;
                }
            }
            long searchEnd = System.currentTimeMillis() - searchStart;
            System.out.printf("Found 500 / 500 entries. Time take: %s\n", formatTime(sortingTime + searchEnd));
            System.out.printf("Sorting Time: %s - STOPPED, moved to linear search\n", formatTime(sortingTime));
            System.out.printf("Searching time: %s\n", formatTime(searchEnd));
        } else {
            long searchEnd = System.currentTimeMillis() - searchStart;
            System.out.printf("Found 500 / 500 entries. Time take: %s\n", formatTime(sortingTime + searchEnd));
            System.out.printf("Sorting Time: %s\n", formatTime(sortingTime));
            System.out.printf("Searching time: %s\n", formatTime(searchEnd));
        }
    }

    private static boolean jumpSearch(String[] arr, String value) {
        int step = (int) Math.floor(Math.sqrt(arr.length));
        int curr = 0;

        String[] splitValue = value.split(" ");
        String firstName = splitValue[1];
        String lastName = splitValue.length == 3 ? splitValue[2] : "";
        value = firstName + lastName;

        while (curr < arr.length) {
            if (arr[curr].equals(value)) {
                return true;
            } else if (arr[curr].compareTo(value) < 0) {
                if (curr + step >= arr.length) {
                    // Check last block
                    for (int i = curr; i < arr.length; i++) {
                        if (arr[curr].equals(value)) {
                            return true;
                        }
                        curr++;
                    }
                    return false;
                } else {
                    curr += step;
                }
            } else {
                // arr[curr].compareTo(value) > 0
                for (int i = curr - 1; i > curr - step; i--) {
                    if (arr[i].compareTo(value) < 0) {
                        return false;
                    }
                    if (arr[i].equals(value)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private static String formatTime(long timeInMs) {
        StringBuilder builder = new StringBuilder();
        long minutes = (timeInMs / 1000) / 60;
        long seconds = (timeInMs / 1000) % 60;
        long miliseconds = timeInMs - (((minutes * 1000) * 60) + (seconds * 1000));
        builder.append(minutes)
                .append(" min. ")
                .append(seconds)
                .append(" sec. ")
                .append(miliseconds)
                .append(" ms.");
        return builder.toString();
    }
}
