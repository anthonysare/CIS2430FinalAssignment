package univ.Student;

import univ.Calendar.Course;

public class Attempt {
    private Course course;
    private String semester;
    private String grade;

    public Attempt(){
        this.course = new Course();
        this.semester = null;
        this.grade = null;
    }

    public Attempt(Course userCourse, String semester, String grade){
        this.course = userCourse;
        this.semester = semester;
        this.grade = grade;
    }

    public void setAttemptGrade(String grade) {
        if (grade == null) {
            this.grade = null;
            return;
        }

        int gradeNum;
        try {
            gradeNum = Integer.parseInt(grade);
            if (gradeNum <= 100 && gradeNum >= 0) {
                this.grade = grade;
            }
        } catch (Exception ignored) {
            System.out.println("Grades must be between 0 and 100.");
        }
    }

    public String getAttemptGrade() {
        return grade;
    }

    public void setSemesterTaken(String semester) {
        if (semester != null && !semester.isEmpty()) {
            this.semester = semester;
        }
    }

    public String getSemesterTaken() {
        return semester;
    }

    public void setCourseAttempted(Course theCourse) {
        if(theCourse != null) {
            this.course = theCourse;
        }
    }

    public Course getCourseAttempted() {
        return course;
    }

    public String getAttemptStatus(){
        try{
            double g = Double.parseDouble(grade);
            if(g >= 50.00){
                return "Completed";
            }else{
                return "Failed";
            }
        } catch(NumberFormatException nfe){
            return "NA";
        }
    }
}

