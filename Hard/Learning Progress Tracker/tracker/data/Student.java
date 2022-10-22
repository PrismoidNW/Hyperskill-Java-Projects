package tracker.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Student {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int[] points;
    private final double[] courseCompletion;
    private int studentId;

    public Student(short studentId, String firstName, String lastName, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.points = new int[4];
        this.courseCompletion = new double[4];
    }

    public int[] getPoints() {
        return points;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getPoint(Course course) {
        String courseName = course.getName();
        return courseName.equals("Java") ? points[0] :
                courseName.equals("DSA") ? points[1] :
                        courseName.equals("Databases") ? points[2] :
                                points[3];
    }

    public double getCourseCompletion(Course course) {
        String courseName = course.getName();
        return courseName.equals("Java") ? courseCompletion[0] :
                courseName.equals("DSA") ? courseCompletion[1] :
                        courseName.equals("Databases") ? courseCompletion[2] :
                                courseCompletion[3];
    }


    public void setId(int id) {
        this.studentId = id;
    }

    public void addPoint(Course course, int point) {

        int currentPoint = getPoint(course);
        int newPoint = currentPoint + point;

        setPoint(course, newPoint);
        updateCourseCompletion(course);
    }

    public void updateCourseCompletion(Course course) {
        int currentPoint = getPoint(course);
        double newPercent = (double) currentPoint * 100 / course.getCompletionPoint();

        BigDecimal bd = new BigDecimal(Double.toString(newPercent));
        bd.setScale(1, RoundingMode.HALF_UP);
        newPercent = bd.doubleValue();

        setCourseCompletion(course, newPercent);
    }


    private void setPoint(Course course, int newPoint) {
        switch (course.getName()) {
            case "Java":
                points[0] = newPoint;
                break;
            case "DSA":
                points[1] = newPoint;
                break;
            case "Databases":
                points[2] = newPoint;
                break;
            case "Spring":
                points[3] = newPoint;
                break;
        }
    }

    private void setCourseCompletion(Course course, double newPercent) {
        switch (course.getName()) {
            case "Java":
                courseCompletion[0] = newPercent;
                break;
            case "DSA":
                courseCompletion[1] = newPercent;
                break;
            case "Databases":
                courseCompletion[2] = newPercent;
                break;
            case "Spring":
                courseCompletion[3] = newPercent;
                break;
        }
    }

    @Override
    public String toString() {
        return String.format("%d points: Java=%d; DSA=%d, Databases=%d; Spring=%d", this.studentId,
                this.getPoint(Course.JAVA),
                this.getPoint(Course.DSA), this.getPoint(Course.DATABASE), this.getPoint(Course.SPRING)
        );
    }

    public boolean isValidEmail() {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e" +
                "-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "+)\\])";
        return this.email.matches(emailRegex);
    }

    public boolean isFirstNameValid() {
        String validRegex = "[A-Za-z]+[\\-']{0,1}[A-Za-z]+";
        String adjacentRegex = "[A-Za-z]+[\\-']{2,}[A-Za-z]+";
        return this.firstName.matches(validRegex) && !firstName.matches(adjacentRegex);
    }

    public boolean isLastNameValid() {
        String validRegex = "[A-Za-z]+[\\-']{0,1}[A-Za-z]*[\\-']{0,1}[A-Za-z]+";
        String adjacentRegex = "[A-Za-z]+[\\-']{2,}[A-Za-z]+";
        return this.lastName.matches(validRegex) && !lastName.matches(adjacentRegex);
    }
}
