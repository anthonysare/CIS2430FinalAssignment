package database;
import univ.Calendar.Course;
import univ.Calendar.CourseCatalog;

import java.util.ArrayList;
public class DBConnect {

    private String pwd;
    private String user;
    

    public DBConnect(){
        pwd = DBDetails.password;
        user = DBDetails.username;
    }

    /**
     * Anthony
     * Initialize DB and return arraylist of courses
     * @return Arraylist of courses
     */
    public ArrayList<String> initializeDB(){
        MyConnection c = new MyConnection(user, pwd);
        boolean fullyResetTables = false;
        PrepStudentScript initTables = new PrepStudentScript(fullyResetTables);
        //c.repopulateCourses();


        ArrayList<String> courseList = new ArrayList();
        return c.getAllCourses();

    }
    /**
     * Anthony
     * Update array with DB courses
     * @return 
     */
    public String[] arrayUpdate(){
        MyConnection c = new MyConnection(user, pwd);
        ArrayList courses = new ArrayList(c.getAllCourses());
        String[] stringList = (String[])courses.toArray(new String[courses.size()]);
        return stringList;
    }



}
