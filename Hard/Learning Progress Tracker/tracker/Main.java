package tracker;

import tracker.data.Course;
import tracker.data.Student;
import tracker.data.ValidCommands;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static tracker.data.Course.*;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    private static final Main main = new Main();
    public static Course[] courses = {JAVA, DSA, DATABASE, SPRING};
    private final List<Student> students = new ArrayList<>();
    private final boolean isAddingPoints = false;

    public static void main(String[] args) throws IOException {

        System.out.println("Learning Progress Tracker");

        try (scanner) {
            while (scanner.hasNextLine()) {
                String cmd = scanner.nextLine();

                if (cmd.equalsIgnoreCase("EXIT")) {
                    System.out.println("Bye!");
                    break;
                } else if (cmd.equalsIgnoreCase("BACK")) {
                    System.out.println("Enter 'exit' to exit the program.");
                    continue;
                }
                if (cmd.isBlank()) {
                    System.out.println("No input.");
                    continue;
                }

                if (ValidCommands.isValidCommand(cmd)) {
                    main.handleCommands(cmd);
                } else {
                    if (!main.isAddingPoints) {
                        System.out.println("Error: unknown command!");
                    }
                }
            }
        }
    }

    private static void printCourseStatistics() {
        boolean dataAvailable = false;

        for (Course course : courses) {
            if (course.getEnrolledStudentNum() > 0) {
                dataAvailable = true;
                break;
            }
        }
        System.out.println("Type the name of a course to see details or 'back' to quit:");

        if (!dataAvailable) {
            System.out.println("Most popular: n/a\n" +
                    "Least popular: n/a\n" +
                    "Highest activity: n/a\n" +
                    "Lowest activity: n/a\n" +
                    "Easiest course: n/a\n" +
                    "Hardest course: n/a");
        } else {


            int highestEnrollment = Arrays.stream(courses)
                    .map(Course::getEnrolledStudentNum).
                    max(Integer::compareTo).get();

            List<String> mostPopularCourses = Arrays.stream(courses)
                    .filter(course -> course.getEnrolledStudentNum() == highestEnrollment)
                    .map(Course::getName)
                    .collect(Collectors.toList());

            int lowestEnrollment = Arrays.stream(courses)
                    .map(Course::getEnrolledStudentNum)
                    .min(Integer::compareTo).get();

            List<String> leastPopularCourses = Arrays.stream(courses)
                    .filter(course -> course.getEnrolledStudentNum() == lowestEnrollment)
                    .map(Course::getName)
                    .filter(name -> !mostPopularCourses.contains(name))
                    .collect(Collectors.toList());

            int highestActivity = Arrays.stream(courses)
                    .map(Course::getTotalSubmission)
                    .max(Integer::compareTo).get();

            List<String> highestActivityCourses = Arrays.stream(courses)
                    .filter(course -> course.getTotalSubmission() == highestActivity)
                    .map(Course::getName)
                    .collect(Collectors.toList());

            int lowestActivity = Arrays.stream(courses)
                    .map(Course::getTotalSubmission)
                    .min(Integer::compareTo).get();

            List<String> lowestActivityCourses = Arrays.stream(courses)
                    .filter(course -> course.getTotalSubmission() == lowestActivity)
                    .map(Course::getName)
                    .filter(name -> !highestActivityCourses.contains(name))
                    .collect(Collectors.toList());

            double highestAveragePoint = Arrays.stream(courses)
                    .filter(course -> course.getEnrolledStudentNum() > 0)
                    .map(Course::getAveragePoint)
                    .max(Double::compareTo).get();


            List<String> easiestCourses = Arrays.stream(courses)
                    .filter(course -> course.getEnrolledStudentNum() > 0)
                    .filter(course -> course.getAveragePoint() == highestAveragePoint)
                    .map(Course::getName)
                    .collect(Collectors.toList());

            double lowestAveragePoint = Arrays.stream(courses)
                    .filter(course -> course.getEnrolledStudentNum() > 0)
                    .map(Course::getAveragePoint)
                    .min(Double::compareTo).get();

            List<String> hardestCourses = Arrays.stream(courses)
                    .filter(course -> course.getEnrolledStudentNum() > 0)
                    .filter(course -> course.getAveragePoint() == lowestAveragePoint)
                    .map(Course::getName)
                    .filter(name -> !easiestCourses.contains(name))
                    .collect(Collectors.toList());


            String mostPopular = mostPopularCourses.isEmpty() ? "n/a" : String.join(", ", mostPopularCourses);
            String leastPopular = leastPopularCourses.isEmpty() ? "n/a" : String.join(", ", leastPopularCourses);
            String mostActivity = highestActivityCourses.isEmpty() ? "n/a" : String.join(", ", highestActivityCourses);
            String leastActivity = lowestActivityCourses.isEmpty() ? "n/a" : String.join(", ", lowestActivityCourses);
            String easiest = easiestCourses.isEmpty() ? "n/a" : String.join(", ", easiestCourses);
            String hardest = hardestCourses.isEmpty() ? "n/a" : String.join(", ", hardestCourses);

            System.out.println("Most popular: " + mostPopular);
            System.out.println("Least popular: " + leastPopular);
            System.out.println("Highest activity: " + mostActivity);
            System.out.println("Lowest activity: " + leastActivity);
            System.out.println("Easiest course: " + easiest);
            System.out.println("Hardest course: " + hardest);


        }

        Scanner sc = new Scanner(System.in);
        boolean back = false;
        String input;

        while (!back) {
            input = sc.nextLine();

            switch (input) {
                case "Java":
                    JAVA.printCourseStatistics();
                    break;
                case "DSA":
                    DSA.printCourseStatistics();
                    break;
                case "Databases":
                    DATABASE.printCourseStatistics();
                    break;
                case "Spring":
                    SPRING.printCourseStatistics();
                    break;
                case "back":
                    back = true;
                case "exit":
                    System.out.println("Enter 'exit' to exit the program");
                    break;
                default:
                    System.out.println("Unknown course.");
            }
        }
    }

    private static boolean isCorrectPointFormat(String input) {
        String[] parts = input.split("\\s+");

        if (parts.length != 5) {
            return false;
        }


        for (int i = 1; i < 5; i++) {

            try {
                int point = Integer.parseInt(parts[i]);

                if (point < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static void notifyCommand() {
        List<Student> notifiedStudent = new ArrayList<>();

        for (Course course : courses) {
            List<Student> studentsToNotify = course.getStudentsToNotify();

            if (!studentsToNotify.isEmpty()) {
                for (Student student : studentsToNotify) {
                    System.out.printf("To: %s%n", student.getEmail());
                    System.out.println("Re: Your Learning Progress");
                    System.out.printf(
                            "Hello, %s %s! You have accomplished our %s course!%n",
                            student.getFirstName(), student.getLastName(), course.getName()
                    );

                }
            }

            notifiedStudent.addAll(studentsToNotify);
        }

        long totalNotifiedStudent = notifiedStudent.stream()
                .distinct()
                .count();

        System.out.printf("Total %d students have been notified.%n", totalNotifiedStudent);
    }

    private void handleCommands(String command) {
        switch (ValidCommands.getCommand(command)) {
            case ADD_STUDENTS -> addStudents();
            case LIST -> list();
            case ADD_POINTS -> addStudentPoint();
            case FIND -> find();
            case STATISTICS -> printCourseStatistics();
            case NOTIFY -> notifyCommand();
        }
    }

    private void find() {
        System.out.println("Enter an id or 'back' to return:");

        loop1:
        while (true) {
            String studentId = scanner.next();

            if (studentId.equalsIgnoreCase("BACK")) {
                break;
            }
            for (Student student : students) {
                if (studentId.equals(String.valueOf(student.getStudentId()))) {
                    System.out.println(student);
                    continue loop1;
                }
            }

            System.out.printf("No student is found for id=%s.\n", studentId);
        }
    }

    private void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");
        handleAddStudent();
        System.out.println("Total " + students.size() + " students have been added.");
    }

    private void list() {
        if (students.size() == 0) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            students.forEach(e -> System.out.println(e.getStudentId()));
        }
    }

    public void addStudentPoint(String studentId, int[] points) {
        if (!studentExists(studentId)) {
            System.out.printf("No student is found for id=%s.%n\n", studentId);
        } else {
            Student student = getStudent(studentId);

            for (int i = 0; i < points.length; i++) {
                if (points[i] > 0) {
                    student.addPoint(courses[i], points[i]);

                    courses[i].addEnrolledStudentIfNotYet(student);
                    courses[i].addTotalPoint(points[i]);
                }
            }
            System.out.println("Points updated.");
        }
    }

    private void addStudentPoint() {
        boolean back = false;
        String input;

        System.out.println("Enter an id and points or 'back' to return:");
        while (!back) {
            input = scanner.nextLine();

            if ("back".equals(input)) {
                back = true;
            }
            if (isCorrectPointFormat(input)) {
                String[] parts = input.split("\\s+");
                String studentId = parts[0];

                int[] points = {Integer.parseInt(parts[1])
                        , Integer.parseInt(parts[2])
                        , Integer.parseInt(parts[3])
                        , Integer.parseInt(parts[4])};


                this.addStudentPoint(studentId, points);
            } else {
                System.out.println("Incorrect points format.");
            }
        }
    }

    private Student getStudent(String id) {
        for (Student s : students) {
            if (s.getStudentId() == Integer.parseInt(id)) {
                return s;
            }
        }
        return null;
    }

    private void handleAddStudent() {
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("BACK")) {
                break;
            }

            String[] splitInput = input.split(" ");

            List<String> splitInputList = new ArrayList<>(List.of(splitInput));

            String firstName = splitInput[0];
            splitInputList.remove(firstName);

            String email = splitInput[splitInput.length - 1];
            splitInputList.remove(email);

            StringBuilder builder = new StringBuilder();
            splitInputList.forEach(builder::append);
            String lastName = builder.toString();


            Student student = new Student(generateId(), firstName, lastName, email);

            if ((!student.isValidEmail() && !student.isFirstNameValid() && !student.isLastNameValid()) || splitInput.length < 3) {
                System.out.println("Incorrect credentials.");
            } else if (!student.isValidEmail()) {
                System.out.println("Incorrect email.");
            } else if (emailExists(student.getEmail())) {
                System.out.println("This email is already taken.");
            } else if (!student.isFirstNameValid()) {
                System.out.println("Incorrect first name.");
            } else if (!student.isLastNameValid()) {
                System.out.println("Incorrect last name.");
            } else {
                students.add(student);
                System.out.println("The student has been added.");
            }
        }
    }

    private boolean studentExists(String id) {
        try {
            Integer.valueOf(id);
        } catch (Exception e) {
            return false;
        }
        for (Student student : students) {
            if (student.getStudentId() == Integer.parseInt(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean emailExists(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private short generateId() {
        Random idGenerator = new Random();
        short id;
        do {
            id = (short) idGenerator.nextInt(Short.MAX_VALUE);
        } while (studentExists(String.valueOf(id)));
        return id;
    }
}
