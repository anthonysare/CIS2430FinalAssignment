package univ.Calendar;

import java.util.ArrayList;

public class BCG extends GeneralDegree {

    private static final double maxOneSubjectCredits = 11.00;
    private static final double max1000LvlCredits = 6.00;
    private static final double rqrd3000orHigherCredits = 4.00;
    private static final double rqrdCisStat2000orHigherCredits = 0.5;
    private static final double rqrdScienceCredits = 2.00;
    private static final double rqrdArtsSocialScienceCredits = 2.00;
    private Course course;

    public BCG() {
        super();
        course = new Course();
        this.setDegreeTitle("BCG");
    }

    public void fillListOfRequiredCourseCodes() {

        course = new Course("CIS1500");
        this.getRequiredCourses().add(course);
        course = new Course("CIS1910");
        this.getRequiredCourses().add(course);
        course = new Course("CIS2430");
        this.getRequiredCourses().add(course);
        course = new Course("CIS2500");
        this.getRequiredCourses().add(course);
        course = new Course("CIS2520");
        this.getRequiredCourses().add(course);
        course = new Course("CIS2750");
        this.getRequiredCourses().add(course);
        course = new Course("CIS2910");
        this.getRequiredCourses().add(course);
        course = new Course("CIS3530");
        this.getRequiredCourses().add(course);
    }

    public void setRequiredCourses(ArrayList<Course> toSet) {
        Course toConvert = new Course();
        for (Course c : toSet) {
            toConvert.setCourseCode(c.getCourseCode());
        }
    }


    public boolean meetsRequirements(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        double totalCredits = 0.0, credits3000 = 0.0, credits1000 = 0.0, creditsSubject = 0.0, creditsCisStat2000 = 0.0 ;
        String[] courseCodeParts;
        for (Course c : allTheCoursesPlannedAndTaken) {
            courseCodeParts = c.getCourseCode().split("\\*", 2);
            if (courseCodeParts[0].equals("CIS")) {
                creditsSubject += c.getCourseCredit();
            }
            if (Double.parseDouble(courseCodeParts[1]) < 2000) {
                credits1000 += c.getCourseCredit();
            }
            if (Double.parseDouble(courseCodeParts[1]) >= 3000) {
                credits3000 += c.getCourseCredit();
            }
            if ((courseCodeParts[0].equals("CIS") || courseCodeParts[0].equals("STAT")) && Double.parseDouble(courseCodeParts[1]) >= 2000) {
                creditsCisStat2000 += c.getCourseCredit();
            }
            if (creditsSubject < maxOneSubjectCredits && credits1000 < max1000LvlCredits) {
                totalCredits += c.getCourseCredit();
            }

        }
        return totalCredits >= GeneralDegree.getRqrdNumberOfCredits() && credits3000 >= rqrd3000orHigherCredits && creditsCisStat2000 >= rqrdCisStat2000orHigherCredits;
    }


    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if (this.getDegreeTitle() != null) {
            toString = new StringBuilder(("Code: " + this.getDegreeTitle() + System.getProperty("line.separator")));
        }
        if (this.getRequiredCourses() != null) {
            toString.append("Required BCG univ.Calendar.Course Codes: ");
            for (Course c : this.getRequiredCourses()) {
                toString.append(c).append(" ");
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

        if (!(o instanceof Degree)) {
            return false;
        }

        BCG bcg = (BCG) o;
        if (!(this.getDegreeTitle().equals(bcg.getDegreeTitle()))) {
            return false;
        }
        return this.getRequiredCourses().equals(bcg.getRequiredCourses());
    }


}
