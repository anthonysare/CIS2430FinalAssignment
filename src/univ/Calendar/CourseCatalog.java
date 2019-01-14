package univ.Calendar;

import database.DBConnect;
import database.DBDetails;
import database.MyConnection;

import java.util.*;

public class CourseCatalog {

    private MyConnection mc;

    public CourseCatalog() {
        this.mc = new MyConnection(DBDetails.username, DBDetails.password);
    }

    public void addCourse(Course toAdd) {
        if (findCourse(toAdd.getCourseCode()) == null) {
            String prerequisites = "";
            for (Course c : toAdd.getPrerequisites()) {
                prerequisites = prerequisites.concat(c.getCourseCode() + ":");

            }
            prerequisites = prerequisites.substring(0, prerequisites.length() - 1);
            mc.addCourse(toAdd.getCourseCode(), Double.toString(toAdd.getCourseCredit()), toAdd.getCourseTitle(), toAdd.getSemesterOffered(), prerequisites);

        } else {
            System.out.println("Course already added\n");
        }
    }

    public void removeCourse(Course toRemove) {
        if (findCourse(toRemove.getCourseCode()) != null) {
            mc.deleteCourse(toRemove.getCourseCode());
        } else {
            System.out.println("Course not found");
        }
    }

    public Boolean isEmpty() {
        return mc.getAllCourses().isEmpty();
    }

    public Course findCourse(String courseCode) {
        String toFind = mc.findCourse(courseCode);
        ArrayList<Course> prereq = new ArrayList();
        if (toFind != null) {
            String split[] = toFind.split(",");
            if (split.length > 4) {
                String preReqSplit[] = split[4].split(":");
                for (String s : preReqSplit) {
                    Course temp = new Course(s);
                    prereq.add(temp);
                }
            }
            Course courseToAdd = new Course(split[0], split[2], Double.parseDouble(split[1]), split[3], prereq);
            return courseToAdd;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if (!this.isEmpty()) {
            toString.append("univ.Calendar.Course Catalog: ");
            for (String s : this.mc.getAllCourses()) {
                toString.append(s);
            }
            toString.append(System.getProperty("line.separator"));
        }
        return toString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof CourseCatalog)) {
            return false;
        }

        ArrayList<String> courseCat = ((CourseCatalog) o).mc.getAllCourses();

        return this.mc.getAllCourses().equals(courseCat);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.mc);
        return hash;
    }

}
