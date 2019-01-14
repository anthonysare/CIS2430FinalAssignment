package univ.Calendar;

import java.util.ArrayList;

public abstract class GeneralDegree extends Degree {
    private static final double rqrdNumberOfCredits = 15.00;


    GeneralDegree() {
        super();
    }


    protected static double getRqrdNumberOfCredits() {
        return rqrdNumberOfCredits;
    }

    public double numberOfCreditsRemaining(ArrayList<Course> allTheCoursesPlannedAndTaken) {
        System.out.println("test1\n");
        double remainingCredits = rqrdNumberOfCredits;
        System.out.println("test1\n");
        for (Course c : allTheCoursesPlannedAndTaken) {
            System.out.println(c.toString());
            remainingCredits -= c.getCourseCredit();
        }

        return remainingCredits;
    }

}
