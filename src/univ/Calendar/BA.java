package univ.Calendar;

import java.util.ArrayList;

public class BA extends HonoursDegree {

    @Override
    public boolean meetsRequirements(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        return false;
    }

    @Override
    public double numberOfCreditsRemaining(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        return 0;
    }

    @Override
    public ArrayList<Course> remainingRequiredCourses(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
