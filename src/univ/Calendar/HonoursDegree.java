package univ.Calendar;

import univ.Calendar.Course;
import univ.Calendar.Degree;

import java.util.ArrayList;

public abstract class HonoursDegree extends Degree {
    private static final double rqrdNumberOfCredits = 20.00;


    HonoursDegree() {
        super();
    }

    public static double getRqrdNumberOfCredits() {
        return rqrdNumberOfCredits;
    }

    public double numberOfCreditsRemaining(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        double remainingCredits = rqrdNumberOfCredits;
        for (Course c : allTheCoursesPlannedAndTaken) {
            remainingCredits -= c.getCourseCredit();
        }

        return remainingCredits;
    }

}
