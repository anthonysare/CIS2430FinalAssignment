package univ.Calendar;

import java.util.ArrayList;

public class SEng extends HonoursDegree {

    private static final double max1000LvlCredits = 6.00;
    private static final double rqrd3000orHigherCredits = 6.00;
    private static final double rqrd4000Lvl = 2.00;
    private static final double rqrdCISCredits = 11.25;
    private Course course;

    public SEng() {
        super();
        course = new Course();
        this.setDegreeTitle("SEng");
    }

    public void fillListOfRequiredCourseCodes() {

        course = new Course("CIS*1250");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*1500");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*1910");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*2250");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*2500");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*2030");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*2430");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*2520");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*3250");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*2750");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*3110");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*3490");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*3750");
        this.getRequiredCourses().add(course);
        course = new Course("STAT*2040");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*3760");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*3260");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*4150");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*4300");
        this.getRequiredCourses().add(course);
        course = new Course("CIS*4250");
        this.getRequiredCourses().add(course);
    }


    @Override
    public boolean meetsRequirements(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        double totalCredits = 0.0, credits4000=0.0, credits3000 = 0.0, credits1000 = 0.0, creditsSubject = 0.0;
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
                if(Double.parseDouble(courseCodeParts[1])>=4000){
                    credits4000+= c.getCourseCredit();
                }
            }

            if (Double.parseDouble(courseCodeParts[1]) < 2000 && credits1000 < max1000LvlCredits ) {
                totalCredits += c.getCourseCredit();
            }else{
                totalCredits += c.getCourseCredit();
            }
        }
        return totalCredits >= GeneralDegree.getRqrdNumberOfCredits() && credits3000 >= rqrd3000orHigherCredits
                && creditsSubject>=rqrdCISCredits && credits4000>=rqrd4000Lvl;

    }



    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if (this.getDegreeTitle() != null) {
            toString = new StringBuilder(("Code: " + this.getDegreeTitle() + System.getProperty("line.separator")));
        }
        if (this.getRequiredCourses() != null) {
            toString.append("Required SEng univ.Calendar.Course Codes: ");
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

        SEng seng = (SEng) o;
        if (!(this.getDegreeTitle().equals(seng.getDegreeTitle()))) {
            return false;
        }
        return this.getRequiredCourses().equals(seng.getRequiredCourses());
    }
}
