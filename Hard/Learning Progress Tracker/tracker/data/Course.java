package tracker.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Course {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASE("Databases", 480),
    SPRING("Spring", 550);

    private final String name;
    private final int completionPoint;
    private final List<Student> enrolledStudents;
    private final List<Student> studentHasBeenNotified;
    private int totalSubmission;
    private int totalPoint;

    Course(String name, int completionPoint) {
        this.name = name;
        this.completionPoint = completionPoint;

        this.enrolledStudents = new ArrayList<>();
        this.studentHasBeenNotified = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCompletionPoint() {
        return completionPoint;
    }

    public int getEnrolledStudentNum() {
        return this.enrolledStudents.size();
    }

    public int getTotalSubmission() {
        return totalSubmission;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public double getAveragePoint() {
        if (totalSubmission != 0) {
            return (double) totalPoint / totalSubmission;
        }

        return 0.0;
    }

    public List<Student> getStudentsToNotify() {
        List<Student> studentsToNotify = enrolledStudents.stream()
                .filter(student -> student.getPoint(this) == this.completionPoint)
                .filter(student -> !studentHasBeenNotified.contains(student))
                .collect(Collectors.toList());


        studentHasBeenNotified.addAll(studentsToNotify);
        return studentsToNotify;
    }


    public void addEnrolledStudentIfNotYet(Student student) {
        if (!this.enrolledStudents.contains(student)) {
            this.enrolledStudents.add(student);
        }
    }

    public void addTotalPoint(int point) {
        totalSubmission++;
        totalPoint += point;
    }

    public void sortStudentBasedOnCompletion() {

        Comparator<Student> sortById = Comparator.comparingInt(Student::getStudentId);
        Comparator<Student> sortByCourseCompletion = (s1, s2) -> Double.compare(s2.getCourseCompletion(this),
                s1.getCourseCompletion(this));

        enrolledStudents.sort(sortById);
        enrolledStudents.sort(sortByCourseCompletion);

    }


    public void printCourseStatistics() {
        sortStudentBasedOnCompletion();
        System.out.println(name);
        System.out.println("id  points  completed");
        for (Student student : enrolledStudents) {
            System.out.printf("%-5d %-6d    %.1f%%%n", student.getStudentId(), student.getPoint(this),
                    student.getCourseCompletion(this));
        }
    }
}
