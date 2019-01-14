package univ.Calendar;

import univ.Calendar.Course;
import univ.Calendar.CourseCatalog;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Degree {
    private String title;
    private ArrayList<Course> listOfRequiredCourseCodes;
    private CourseCatalog catalog;

    public Degree() {
        this.title = null;
        this.listOfRequiredCourseCodes = new ArrayList<>();
    }

    public void setDegreeTitle(String title) {
        if (title != null && !title.isEmpty())
            this.title = title;
    }

    public String getDegreeTitle() {
        return this.title;
    }

    public void setRequiredCourses(ArrayList<Course> listOfRequiredCourseCodes) {
        if (listOfRequiredCourseCodes != null && !listOfRequiredCourseCodes.isEmpty())
            this.listOfRequiredCourseCodes = listOfRequiredCourseCodes;
    }

    public ArrayList<Course> getRequiredCourses() {

        ArrayList<Course> requiredCourses = new ArrayList<>();
        Course course = new Course();

        for (Course req : listOfRequiredCourseCodes) {
            course = catalog.findCourse(req.getCourseCode());
            if (course != null) {
                requiredCourses.add(course);
            }
        }

        return requiredCourses;
    }
    
    public static Degree parseDegree(String toParse){
        if(toParse.equals("CS")){
            CS temp = new CS();
            return temp;
        }else if(toParse.equals("BCG")){
            BCG temp = new BCG();
            return temp;
        }if(toParse.equals("SEng")){
            SEng temp = new SEng();
            return temp;
        }if(toParse.equals("THST")){
            THST temp = new THST();
            return temp;
        }else{
            return null;
        }
        
    }

    /**
     * @param code
     * @return
     */
    public ArrayList<Course> getPrerequisites(String code) {

        ArrayList<Course> preReqs = new ArrayList<>();

        for(Course c: listOfRequiredCourseCodes) {

            if(code.equals(c.getCourseCode())) {
                preReqs = c.getPrerequisites();
            }else {
                System.out.println("Not a required course for this major");
            }
        }
        return preReqs;
    }

    public ArrayList<Course> remainingRequiredCourses(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        CourseCatalog catalog = new CourseCatalog();
        ArrayList<Course> remainingRequiredCourses = new ArrayList<>();
        for (Course needed : this.getRequiredCourses()) {
            for (Course c : allTheCoursesPlannedAndTaken) {
                if ((c.getCourseCode() != null && c.getCourseCode().equals(needed.getCourseCode()))) {
                    break;
                }
            }
            if (catalog.findCourse(needed.getCourseCode()) != null) {
                remainingRequiredCourses.add(catalog.findCourse(needed.getCourseCode()));
            } else {
                System.out.println("univ.Calendar.Course not in catalog: " + needed);
            }
        }
        return remainingRequiredCourses;
    }

    public abstract boolean meetsRequirements(ArrayList<Course> allTheCoursesPlannedAndTaken);

    public abstract  double numberOfCreditsRemaining(ArrayList<Course> allTheCoursesPlannedAndTaken);

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(getDegreeTitle());
        hash = 41 * hash + Objects.hashCode(this.getRequiredCourses());
        return hash;
    }

}
