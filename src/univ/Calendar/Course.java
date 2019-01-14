package univ.Calendar;

import java.util.ArrayList;
import java.util.Objects;

public class Course {

    private String courseCode;
    private String courseTitle;
    private double credit;
    private ArrayList<Course> preReqList;
    private String semesterOffered;

    public Course() {
        this.courseCode = null;
        this.courseTitle = null;
        this.semesterOffered = null;
        this.credit = -1;
        this.preReqList = new ArrayList<>();
    }

    public Course(Course course) {
        this.courseCode = course.getCourseCode();
        this.courseTitle = course.getCourseTitle();
        this.credit = course.getCourseCredit();
        this.preReqList = course.getPrerequisites();
        this.semesterOffered = course.getSemesterOffered();
    }
    public Course(String courseCode)
    {
        this.courseCode = courseCode;
        this.courseTitle= null;
        this.credit= -1;
        this.preReqList = new ArrayList<Course>();
        this.semesterOffered = null;
    }
        public Course(String Code, String title, double credit, String semester, ArrayList<Course> prereq )
    {
        this.courseCode = Code;
        this.courseTitle= title;
        this.credit= credit;
        this.preReqList = new ArrayList<Course>(prereq);
        this.semesterOffered = semester;
    }
    public boolean isEmpty()
    {
        if(this.credit==-1){
            return true;
        }
        return false;
    }

    protected void setCourseCode(String courseCode) {
        if (courseCode != null && !courseCode.isEmpty()) {
            this.courseCode = courseCode;
        }
    }

    protected void setCourseTitle(String courseTitle) {
        if (courseTitle != null && !courseTitle.isEmpty()) {
            this.courseTitle = courseTitle;
        }
    }

    protected void setCourseCredit(Double credit) {
        if (credit != null && credit >= 0 && credit <= 1.0) {
            this.credit = credit;
        }
    }

    protected void setPrerequisites(ArrayList<Course> preReqList) {
        if(preReqList == null) {
            this.preReqList = null;
        }else{
            this.preReqList = new ArrayList<Course>(preReqList);
        }
    }

    protected void setSemesterOffered(String semesterOffered) {
        if (semesterOffered != null && !semesterOffered.isEmpty()) {
            this.semesterOffered = semesterOffered;
        }
    }

    public String getCourseCode() { return this.courseCode; }

    public String getCourseTitle() { return this.courseTitle; }

    public String getSemesterOffered() { return this.semesterOffered; }

    public double getCourseCredit() { return this.credit; }

    public ArrayList<Course> getPrerequisites() { return this.preReqList; }

    public String toFile() {
        String toFile = "";
        toFile += this.getCourseCode();
        toFile += ",";
        toFile += Double.toString(this.getCourseCredit());
        toFile += ",";
        toFile += this.getCourseTitle();
        toFile += ",";
        toFile += this.getSemesterOffered();
        toFile += ",";

        for (Course preReq : this.getPrerequisites())  {
            toFile += preReq.getCourseCode();
            toFile += ":";
        }
        if (toFile.charAt(toFile.length() - 1) == ':') {
            toFile = toFile.substring(0, toFile.length() - 1);
        }
        toFile += "?";
        return toFile;
    }

    public String serializeCourse(){
        String toString = "";
        toString += this.getCourseCode();
        toString += ",";
        toString += Double.toString(this.getCourseCredit());
        toString += ",";
        toString += this.getCourseTitle();
        toString += ",";
        toString += this.getSemesterOffered();
        toString += ",";

        for (Course preReq : this.getPrerequisites())  {
            toString += preReq.getCourseCode();
            toString += ":";
        }
        return toString;
    }
    
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if (this.courseCode != null) {
            //toString = new StringBuilder(("Code: " + this.courseCode + System.getProperty("line.separator")));
            System.out.println("Code: " + this.courseCode + "\n");
        }
        if (this.semesterOffered != null) {
            toString = new StringBuilder(("Semester Offered: " + this.semesterOffered + System.getProperty("line.separator")));
        }
        if (this.courseTitle != null) {
            toString.append("Title: ").append(this.courseTitle).append(System.getProperty("line.separator"));
        }
        if (this.credit > 0) {
            toString.append("Credit: ").append(this.getCourseCredit()).append(System.getProperty("line.separator"));
        }
        if (this.preReqList != null) {
            toString.append("Prerequisites: ");
            for (Course c : this.preReqList) {
                toString.append(c.getCourseCode()+" ");
            }
        }
        toString.append("\n");
        return toString.toString();
    }
//@Override
//public String toString()
//{
//    String toReturn  = "\nCode: " + this.courseCode +"\nTitle: " +  this.courseTitle + "\nSemester Offered:" + this.semesterOffered + "\nCredit: " + this.credit + "\n";
//
//    return toReturn;
//}


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Course)) {
            return false;
        }

        Course course = (Course) o;

        if (course.courseCode == null || !(this.courseCode.equals(course.courseCode))) {
            return false;
        }

        if (course.semesterOffered == null || !(this.semesterOffered.equals(course.semesterOffered ))) {
            return false;
        }

        if (course.courseTitle == null || !(this.courseTitle.equals(course.courseTitle))) {
            return false;
        }

        return this.preReqList.equals(course.preReqList);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.courseCode);
        hash = 53 * hash + Objects.hashCode(this.courseTitle);
        hash = 53 * hash + Objects.hashCode(this.semesterOffered);
        hash = 53 * hash + Objects.hashCode(this.preReqList);
        return hash;
    }
}
