package htl.teddy_com;

import htl.teddy_com.api.students.StudentManager;

//EinE app wie Web untis und die auch ziemlich ähnlich arbeiten soll und auch mir Schüler app usw....
//eigenes INterface für  lehrer und joa
public class Sanitra {
    public static Sanitra instance = new Sanitra();
    public StudentManager sm;

    public static void main(String[] args) {
        Sanitra.instance.sm = new StudentManager();
        Sanitra.instance.sm.getStudentss();
    }

}