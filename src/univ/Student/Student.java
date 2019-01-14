package univ.Student;

import database.DBStudent;
import univ.Calendar.Course;
import univ.Calendar.CourseCatalog;
import univ.Calendar.Degree;

import java.util.*;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;

public class Student {

    private String first;
    private String last;
    private int studentNum;
    private Degree degree;
    private ArrayList<Attempt> attemptList;
    private HashMap<Course, String> plannedCourseList;
    private CourseCatalog catalogCopy = new CourseCatalog();

    public Student() {
        this.first = null;
        this.last = null;
        this.studentNum = 0;
        this.plannedCourseList = new HashMap();
        this.attemptList = new ArrayList();
    }
    /**
     * Anthony
     * Convert DBStudent to Student Object
     * @param toParse DBStudent to parse
     * @return Student object
     */
    Student fromDBStudent(DBStudent toParse){
        Student newStudent = new Student();
        Degree deg;
        boolean planned = false;
        String[] splitData;
        Attempt tempAttempt = new Attempt();
        Course tempCourse;
        ArrayList<Course> tempPrereqs = new ArrayList();
        String name[] = toParse.getName().split(" ");
        newStudent.setFirstName(name[0]);
        newStudent.setLastName(name[1]);
        deg = Degree.parseDegree(toParse.getDegree());
        newStudent.setDegree(deg);
        newStudent.setStudentNumber(Integer.parseInt(toParse.getId()));
        for(String s: toParse.getCourses()){
            if(s.equals("Planned")){
                planned = true;
            }
            if(!planned){
                splitData = s.split(",");
                tempCourse = new Course();
                String prereqs[] = splitData[4].split(":");
                for(String s2:prereqs ){
                    tempCourse = new Course(s2);
                    tempPrereqs.add(tempCourse);
                }
                
                
            }
        }
        
        return null;
        
    }

    /**
     * Anthony 
     * Convert student object to DBStudent object 
     * @return DBStudent object
     */
    DBStudent toDBStudent() {
        DBStudent temp = new DBStudent(Integer.toString(studentNum), getFullName());
        ArrayList<String> tempList = new ArrayList();
        for (Attempt a : getAttemptList()) {
            tempList.add(serializeAttempt(a));
        }
        tempList.add("Planned");
        String tempString = "";
        for (Map.Entry<Course, String> plannedCourseList : plannedCourseList.entrySet()) {
            Course key = plannedCourseList.getKey();
            String value = plannedCourseList.getValue();
            tempString +=key.serializeCourse();
            tempString += ",";
            tempString +=value;
            tempString+=",";
        }
        temp.setCourses(tempList);
        return temp;
    }

    /**
     * Anthony
     * Convert Attempt Object to String format for database storage
     * @param a Attempt to SErialize 
     * @return 
     */
    public String serializeAttempt(Attempt a) {
        String toString = "";

        toString += a.getCourseAttempted().getCourseCode();
        toString += ",";
        toString += Double.toString(a.getCourseAttempted().getCourseCredit());
        toString += ",";
        toString += a.getCourseAttempted().getCourseTitle();
        toString += ",";
        toString += a.getCourseAttempted().getSemesterOffered();
        toString += ",";

        for (Course preReq : a.getCourseAttempted().getPrerequisites()) {
            toString += preReq.getCourseCode();
            toString += ":";
        }
        toString += ",";
        toString += a.getSemesterTaken();
        toString += ",";
        toString += a.getAttemptGrade();
        return toString;
    }

    public String getFullName() {
        String fullName;
        if (this.first == null && this.last == null) {
            return null;
        } else if (this.first == null) {
            fullName = this.last;
        } else if (this.last == null) {
            fullName = this.first;
        } else {
            fullName = this.first + " " + this.last;
        }
        return fullName;
    }

/**Brayden
     * @return completed credits
     */
    public double getCreditsCompleted() {

        double completed = 0;

        if (attemptList != null) {
            for (int i = 0; i < attemptList.size(); i++) {
                if (attemptList.get(i).getAttemptStatus().equals("Completed")) {
                    completed += attemptList.get(i).getCourseAttempted().getCourseCredit();
                }
            }
        }
        return completed;
    }

    public ArrayList<Course> allTheCoursesPlannedAndTaken() {
        ArrayList<Course> pt = new ArrayList<>();

        // add attempt list to all
        if (attemptList != null) {
            for (int i = 0; i < attemptList.size(); i++) {
                pt.add(attemptList.get(i).getCourseAttempted());
            }
        }

        // add planned list to add
        if (plannedCourseList != null) {
            for (Map.Entry<Course, String> plannedCourseList : plannedCourseList.entrySet()) {
                Course key = plannedCourseList.getKey();
                pt.add(key);
            }
        }
        return pt;
    }

    public void setCourseGrade(String courseCode, String semester, String grade) {
        for (Attempt a : this.getAttemptList()) {
            if (a.getCourseAttempted().getCourseCode() != null && a.getSemesterTaken() != null && a.getCourseAttempted().getCourseCode().equals(courseCode) && a.getSemesterTaken().equals(semester)) {
                a.setAttemptGrade(grade);
                System.out.println("Grade updated.");
                return;
            }
        }
        System.out.println("Grade could not be updated.");
    }

    public Attempt getAttempt(String courseCode, String semester) {
        for (Attempt a : this.getAttemptList()) {
            if (a.getCourseAttempted().getCourseCode() != null && a.getSemesterTaken() != null && a.getCourseAttempted().getCourseCode().equals(courseCode) && a.getSemesterTaken().equals(semester)) {
                return a;
            }
        }
        return null;
    }

    private Course isValidCourse(String courseCode) {
        Course found = this.catalogCopy.findCourse(courseCode);
        if (found != null) {
            return found;
        }
        return null;
    }

    public void removePlannedCourse(String courseCode, String semester) {
        /**
         * https://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap
         */
        Course temp = new Course();
        for (Map.Entry<Course, String> plannedCourseList : plannedCourseList.entrySet()) {
            Course key = plannedCourseList.getKey();
            String value = plannedCourseList.getValue();
            if (key.getCourseCode() != null && value != null && key.getCourseCode().equals(courseCode) && value.equals(semester)) {
                temp = key;
            }

        }

        plannedCourseList.remove(temp);
    }

    public void removeAttemptedCourse(String courseCode, String semester) {
        for (Attempt a : this.getAttemptList()) {
            if (a.getCourseAttempted().getCourseCode() != null && a.getSemesterTaken() != null && a.getCourseAttempted().getCourseCode().equals(courseCode) && a.getSemesterTaken().equals(semester)) {
                this.getAttemptList().remove(a);
                return;
            }
        }
    }

    public void addAttemptedCourse(String courseCode, String semester, String finalGrade) {
        boolean alreadyAdded = false;
        for (Attempt a : this.getAttemptList()) {
            if (a.getCourseAttempted().getCourseCode().equals(courseCode) && a.getSemesterTaken().equals(semester)) {
                alreadyAdded = true;
                System.out.println("Already in the Plan of Study.");
            }
        }

        if (!alreadyAdded) {
            Course found = isValidCourse(courseCode);
            if (found != null) {
                Attempt toAdd = new Attempt(found, semester, finalGrade);
                toAdd.setSemesterTaken(semester);
                System.out.println(toAdd.toString());
                this.getAttemptList().add(toAdd);
                System.out.println(found.getCourseTitle() + " added.");
            } else {
                System.out.println("No such course in the catalog.");
            }
        }
    }

    public void addPlannedCourse(String courseCode, String semester) {
        Course found = isValidCourse(courseCode);

        if (plannedCourseList.putIfAbsent(found, semester) == null && found != null) {
            System.out.println(found.getCourseTitle() + " added");
        } else if (found == null) {
            System.out.println("No such course in the catalog.");
        } else {
            System.out.println("Already in the Plan of Study");
        }

    }

    /**Brayden
     * @return overall GPA
     */
    public double getOverallGPA() {

        double GPA = 0;
        double credits = 0;
        double x = 0;
        double grade = 0;
        double credit = 0;

        if (attemptList != null) {
            for (int i = 0; i < attemptList.size(); i++) {
                grade = Double.parseDouble(attemptList.get(i).getAttemptGrade());
                credit = attemptList.get(i).getCourseAttempted().getCourseCredit();
                x += (grade * credit);
                credits += attemptList.get(i).getCourseAttempted().getCourseCredit();
            }
            GPA = x / credits;
        }
        return GPA;
    }
    
    public double getCisGPA() {

        double GPA = 0;
        double credits = 0;
        double x = 0;
        double grade = 0;
        double credit = 0;
        String code = "";

        if (attemptList != null) {

            for (int i = 0; i < attemptList.size(); i++) {

                code = attemptList.get(i).getCourseAttempted().getCourseCode();

                if (code.charAt(0) == 'C' && code.charAt(1) == 'I' && code.charAt(2) == 'S') {
                    grade = Double.parseDouble(attemptList.get(i).getAttemptGrade());
                    credit = attemptList.get(i).getCourseAttempted().getCourseCredit();
                    x += (grade * credit);
                    credits += attemptList.get(i).getCourseAttempted().getCourseCredit();
                }
            }

            GPA = (x / credits);
        }

        return GPA;
    }
    
    /**Brayden
     * @return recent 10 GPA
     */
    public double recentGPA() {

        double GPA = 0;
        double credits = 0;
        double x = 0;
        double grade = 0;
        double credit = 0;
        int index = 0;

        if(attemptList.size() >= 10 && attemptList != null) {

            index = attemptList.size() - 10;

            for(int i = index; i < attemptList.size(); i++) {

                grade = Double.parseDouble(attemptList.get(i).getAttemptGrade());
                credit = attemptList.get(i).getCourseAttempted().getCourseCredit();
                x += (grade * credit);
                credits += attemptList.get(i).getCourseAttempted().getCourseCredit();
            }
            GPA = (x / credits);
        }

        return GPA;
    }
    
    /**Brayden
     * @return all prerequisites for a course
     */
    public ArrayList<Course> allPrerequisitsNeeded() {

        ArrayList<Course> allPreReqs = new ArrayList<>();
        ArrayList<Course> preReqs = new ArrayList<>();

        if (plannedCourseList != null) {
            for (Map.Entry<Course, String> plannedCourseList : plannedCourseList.entrySet()) {
                Course key = plannedCourseList.getKey();
                preReqs = key.getPrerequisites();
                for (Course c : preReqs) {
                    allPreReqs.add(c);
                }
            }
        }
        return allPreReqs;
    }
    
    /**Brayden
     * @return sorted map values
     */
    public ArrayList<String> sortMap() {

        ArrayList<String> mapValues = new ArrayList<>(plannedCourseList.values());

        Collections.sort(mapValues);

        return mapValues;
    }
    /**
     * @return all courses attempted
     */
    public ArrayList<Course> allCoursesTaken() {

        ArrayList<Course> t = new ArrayList<>();

        // add attempt list to all
        if (attemptList != null) {
            for (int i = 0; i < attemptList.size(); i++) {
                t.add(attemptList.get(i).getCourseAttempted());
            }
        }

        return t;
    }
    
    public Course findCourse(String courseCode) {
        Course found = new Course();
        if ((found = this.catalogCopy.findCourse(courseCode)) != null) {
            return found;
        }
        return null;
    }

    public String getFirstName() {
        return this.first;
    }

    public void setFirstName(String first) {
        if (first != null && !first.isEmpty()) {
            this.first = first;
        }
    }

    public String getLastName() {
        return this.last;
    }

    public void setLastName(String last) {
        if (last != null && !last.isEmpty()) {
            this.last = last;
        }
    }

    public Integer getStudentNumber() {
        return this.studentNum;
    }

    public void setStudentNumber(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public HashMap<Course, String> getPlannedCourseList() {
        return plannedCourseList;
    }

    public void setPlannedCourseList(HashMap<Course, String> plannedCourseList) {
        if (plannedCourseList != null) {
            this.plannedCourseList = new HashMap<>(plannedCourseList);
        }
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        if (degree != null) {
            this.degree = degree;
        }
    }

    public CourseCatalog getCatalog() {
        return catalogCopy;
    }

    public void setCatalog(CourseCatalog catalog) {
        if (catalog != null) {
            this.catalogCopy = catalog;
        }
    }

    @Override
    public String toString() {
        String toString = "";
        if (this.first != null) {
            toString = ("First name: " + this.first + System.getProperty("line.separator"));
        }
        if (this.last != null) {
            toString += ("Last name: " + this.last + System.getProperty("line.separator"));
        }
        toString += ("univ.Student.Student number: " + this.studentNum + System.getProperty("line.separator"));

        return toString;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;
        if (!(this.first.equals(student.first))) {
            return false;
        }
        if (!(this.last.equals(student.last))) {
            return false;
        }
        return this.studentNum == student.studentNum;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.first);
        hash = 37 * hash + Objects.hashCode(this.last);
        hash = 37 * hash + Objects.hashCode(this.studentNum);
        return hash;
    }

    /**
     * @return the attemptList
     */
    public ArrayList<Attempt> getAttemptList() {
        return attemptList;
    }
}
